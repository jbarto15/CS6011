import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/*This is the HTTPResponse class which is the class that gives a response to the client based off their
* request type. If the request is a file request then the response finds the requested file and then
* sends it back to the client. If the request is a web socket request, the response class creates a
* websocket connection, receives the incoming message, adds them to a room, and sends the message
* to all clients in that room */

class HTTPResponse {
    //string to store the filename
    private String filename;
    //file to store the file
    private File htmlFile;

    //input stream to input the file
    private FileInputStream inputFile;

    //input stream for the error file
    private FileInputStream inputErrorFile;

    //an output stream variable
    private OutputStream outputStream;

    private String errorFilePath;

    //variable to store the HTTPRequest object
    private HTTPRequest request;

    //print writer variable
    private PrintWriter output;

    //client socket variable
    private Socket clientSocket;

    //room variable to be used to determine where messages should be sent
    private Room room_;


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


    //method that has no parameters and doesn't return anything. It sends a response to the client
    //...depending on what they're request is
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


    //method to open websocket and send the web socket responses. Takes zero parameters and has no return type
    public void openChat() throws Exception {
        //send the websocket header
        sendWebSocketHeader();

        //start talking binary over the websocket with the client
        while(true) {
            //call the readInData method and store the message that is returned in a string
            String message = readInWebSocketData();

            //call the sendMessage method to send the message back to the client(s)
            sendChatMessage(message);
        }
    }




    //method for sending the header information to the web socket client
    public void sendWebSocketHeader() throws NoSuchAlgorithmException, UnsupportedEncodingException {
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
    }


    //method for reading in the data from the websocket which then returns a string with the message that was sent
    public String readInWebSocketData() throws Exception {
        //data input stream that will read in the bytes from the client socket
        DataInputStream inData = new DataInputStream( clientSocket.getInputStream() );
        //read in the first byte
        byte b0 = inData.readByte();
        //read in the second byte
        byte b1 = inData.readByte();

        //get the opcode and store in opcode variable
        int opcode = b0 & 0x0F;

        //get the payload length by doing bitwise & operation on b1
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

        //read in the next 4 bytes
        byte [] mask = inData.readNBytes(4);
        //read in the payload using the length variable because that helps us know how many bytes to read
        byte [] payload = inData.readNBytes(length);

        //Unmask the message using the unmasking formula
        for (int i = 0; i < payload.length; i++) {
            payload[i] = (byte) (payload[i] ^ mask[i % 4]);
        }

        //turn the message into a string
        String message = new String(payload);
        System.out.println("Just got this message: " + message);

        return message;
    }


    //method to send the message back to the client which takes in a string as a parameter and doesn't return anything
    public void sendChatMessage(String message) {
        //get the room name, user, and type from the message
        String roomName = message.split("\"room\":\"")[1].split("\"")[0];
        String user = message.split("\"user\":\"")[1].split("\"")[0];
        String type = message.split("\"type\":\"")[1].split("\"")[0];

        //if the type is join, then add the client to the room and send a message to everyone in the
        //...room that the new user has joined
        if (type.equals("join")){
            room_ = Room.getRoom(roomName);
            this.room_.addAClient(user, clientSocket);
            this.room_.sendMessage(message);
        }
        //if type is leave, remove user from the room and send a message to all clients in that room
        //...that the user has left
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



