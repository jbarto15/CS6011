import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ClientInfoStatus;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

class HTTPResponse {
    //string to store the filename
    String filename;
    //file to store the file
    File htmlFile;

    //input stream to input the file
    FileInputStream inputFile;

    //input stream for the error file
    FileInputStream inputErrorFile;

    //an output stream variable
    OutputStream outputStream;

    String errorFilePath;

    //variable to store the HTTPRequest object
    HTTPRequest request;

    //print writer variable
    PrintWriter output;

    //client socket variable
    Socket clientSocket;
    Room room_;


    //constructor
    public HTTPResponse(String filename, OutputStream outputStream, HTTPRequest request, Socket client) throws IOException {

        this.outputStream = outputStream;
        this.filename = filename;
        this.htmlFile = new File("/Users/joshbarton/Desktop/MSD2023/CS6011/Week4/Day18/WebSocketEchos/resources/" + filename);
        this.inputFile = new FileInputStream(this.htmlFile);
        this.errorFilePath = "resources/errorHtml.html";
        this.inputErrorFile = new FileInputStream(errorFilePath);
        this.request = request;
        this.output = new PrintWriter(outputStream);
        this.clientSocket = client;

        //create an exception if the htmlFile does not exist
        if (!htmlFile.exists()) {
            IOException e = new IOException("File requested does not exist!");
            throw e;
        }
    }


    public void sendResponse() throws IOException, NoSuchAlgorithmException, Exception {
        //boolean that checks if the web socket key is in my header info hash map
        boolean isWebSocket = request.headerInfo.containsKey("Sec-WebSocket-Key");

        //if the request is a websocket request, send back the right header info
        if (isWebSocket) {
            openChat(); // Note: this routine never ends...
        }

        if (htmlFile.exists() && !isWebSocket) {
            //give the header information to the client
            outputStream.write("HTTP/1.1 200 OK\n".getBytes());
            //variable that will store a split of the filename so that just the type of file is given
            String[] parts = filename.split("\\.");
            String filetype = parts[parts.length - 1];
            outputStream.write(("Content-Type: text/" + filetype + "\n").getBytes());
            outputStream.write("\n".getBytes());
            //using the transferTo method in the input FileInputStream class, transfer the requested file directly to the client output stream
            //inputFile.transferTo(outputStream);

            for( int i = 0; i < htmlFile.length(); i++ ) {
                outputStream.write(inputFile.read() );
                outputStream.flush();
            }

            //flush the output stream
            //outputStream.flush();

            //close the output stream
            outputStream.close();

        } else {
            //give the header information to the client and the file
            outputStream.write("HTTP/1.1 404 Not Found\n".getBytes());
            outputStream.write("Content-Type: text/html\n".getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write("404 Not Found\n".getBytes());
            inputErrorFile.transferTo(outputStream);

            //flush the output stream
            outputStream.flush();

            //close the output stream
            outputStream.close();
        }
    }


    //method to send the web socket response
    public void openChat() throws Exception {
        //store the magic string that will be added to the web socket key
        String magicString = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        //store the encode key
        String encodeKey = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1")
                .digest((request.headerInfo.get("Sec-WebSocket-Key") + magicString).getBytes("UTF-8")));
        //send the appropriate header info
        output.print("HTTP/1.1 101 Switching Protocols\r\n");
        output.print("Upgrade: websocket\r\n");
        output.print("Connection: Upgrade\r\n");
        output.print("Sec-WebSocket-Accept: " + encodeKey + "\r\n");
        output.print("\r\n"); // send blank line / end of headers
        output.flush();
        //check to see if the header was sent
        System.out.println("Header was sent");


        // start talking binary over the ws with the client
        while(true) {
            //data input stream that will read in the bytes from the client socket
            DataInputStream in = new DataInputStream( clientSocket.getInputStream() ); // note we need the whole socket, not just the in stream
            //read in the first byte
            byte b0 = in.readByte();
            //read in the second byte
            byte b1 = in.readByte();

            //get the opcode
            int opcode = b0 & 0x0F;

            //get the payload length by doing bitwise and operation on b1
            int length = b1 & 0x7F;
            System.out.println("Got a msg from the client with length: " + length);

            //check to see if the payload length is shorter than 126, if so, the length is equal to b1 & 0x7F
            if (length < 126) {
                length = b1 & 0x7F;
            } else if (length == 126) {
                length = in.readShort();
            } else {
                length = (int) in.readLong();
            }

            //boolean variable that lets us know if we have a mask or not
            boolean hasMask = ((b1 & 0x80) != 0);

            //if there is not a mask, then print an error
            if (!hasMask) {
                System.out.println("Error!!!!");
                throw new Exception("unmasked msg from client");
            }

            //Print the opcode and the length
            System.out.println("opcode: " + opcode + ", length: " + length);

            //read in 4 more bytes
            byte [] mask = in.readNBytes(4);
            byte [] payload = in.readNBytes(length);

            System.out.println("payload length: " + payload.length);

            //unmask the message
            for (int i = 0; i < payload.length; i++) {
                payload[i] = (byte) (payload[i] ^ mask[i % 4]);
            }

            //turn the message into a string
            String message = new String(payload);
            System.out.println("Just got this message: " + message);

            String roomName = message.split("\"room\":\"")[1].split("\"")[0];
            String user = message.split("\"user\":\"")[1].split("\"")[0];
            String type = message.split("\"type\":\"")[1].split("\"")[0];
            if(type.equals("join")){
                room_ = Room.getRoom(roomName);
                this.room_.addAClient(user, clientSocket);
                this.room_.sendMessage(message);
            }
            else if(type.equals("leave")){
                this.room_.removeClient(user, clientSocket);
                this.room_.sendMessage(message);
            }
            else{
                this.room_.sendMessage(message);
            }


            //create a data output stream to stream the message back out
//            DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());
//            //send the first byte of the header
//            dataOut.writeByte(0x81);
//            //send the length of the message
//            dataOut.writeByte(message.length());  //something is going on with this line
//            //send the message
//            dataOut.writeBytes(message);

        }
    }

}



