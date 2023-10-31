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

    //room variable to be used to determine where messages should be sent
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

        //if the request is a websocket request, open the web socket chat
        if (isWebSocket) {
            openChat();
        }

        //if the request is a file request and not a web socket request, send back the files requested
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

        }
        //if we don't have the file they requested, send back the error header info and error file
        else {
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


    //method to open websocket and send the web socket responses
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


        // start talking binary over the websocket with the client
        while(true) {
            //data input stream that will read in the bytes from the client socket
            DataInputStream inData = new DataInputStream( clientSocket.getInputStream() );
            //read in the first byte
            byte b0 = inData.readByte();
            //read in the second byte
            byte b1 = inData.readByte();

            //get the opcode
            int opcode = b0 & 0x0F;

            //get the payload length by doing bitwise and operation on b1
            int length = b1 & 0x7F;
            //System.out.println("Got a msg from the client with length: " + length);

            //check to see if the payload length is shorter than 126, if so, the length is equal to b1 & 0x7F
            if (length < 126) {
                length = b1 & 0x7F;
            } else if (length == 126) {
                length = inData.readShort();
            } else {
                length = (int) inData.readLong();
            }

            //boolean variable that lets us know if we have a mask or not
            boolean hasMask = ((b1 & 0x80) != 0);

            //if there is not a mask, then print an error
            if (!hasMask) {
                System.out.println("Error!");
                throw new Exception("Unmasked message from the client.");
            }

            //Print the opcode and the length
            //System.out.println("opcode: " + opcode + ", length: " + length);

            //read in 4 more bytes
            byte [] mask = inData.readNBytes(4);
            //read in the payload using the length variable because that helps us know how many bytes to read
            byte [] payload = inData.readNBytes(length);

            //System.out.println("payload length: " + payload.length);

            //Unmask the message using the unmasking formula
            for (int i = 0; i < payload.length; i++) {
                payload[i] = (byte) (payload[i] ^ mask[i % 4]);
            }

            //turn the message into a string
            String message = new String(payload);
            System.out.println("Just got this message: " + message);

            //get the room name, user, and type from the message
            String roomName = message.split("\"room\":\"")[1].split("\"")[0];
            String user = message.split("\"user\":\"")[1].split("\"")[0];
            String type = message.split("\"type\":\"")[1].split("\"")[0];

            //if the type is join, then add the client to the room and send a message to everyone in the
            //room that the new user has joined
            if (type.equals("join")){
                room_ = Room.getRoom(roomName);
                this.room_.addAClient(user, clientSocket);
                this.room_.sendMessage(message);
            }
            //if type is leave, remove user from the room and send a message to all clients in that room
            //that the user has left
            else if (type.equals("leave")){
                this.room_.removeClient(user, clientSocket);
                this.room_.sendMessage(message);
            }
            //send the message to everyone in the room
            else {
                this.room_.sendMessage(message);
            }
        }
    }

}



