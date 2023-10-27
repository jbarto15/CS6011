import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

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
    PrintWriter outputHeader;


    //constructor
    public HTTPResponse(String filename, OutputStream outputStream, HTTPRequest request) throws IOException {

        this.outputStream = outputStream;
        this.filename = filename;
        this.htmlFile = new File("/Users/joshbarton/Desktop/MSD2023/CS6011/Week4/Day18/WebSocketEchos/resources/" + filename);
        this.inputFile = new FileInputStream(this.htmlFile);
        this.errorFilePath = "resources/errorHtml.html";
        this.inputErrorFile = new FileInputStream(errorFilePath);
        this.request = request;
        this.outputHeader = new PrintWriter(outputStream);

        //create an exception if the htmlFile does not exist
        if (!htmlFile.exists()) {
            IOException e = new IOException("File requested does not exist!");
            throw e;
        }
    }


    //stream in the file if the file exists and then transfer it to the output stream to the client
    public void streamInFile() throws IOException {
        //create a File for the error file
        File errorFile = new File(errorFilePath);
        //if filename == / or /index.html then stream in the file
        if (htmlFile.exists()) {
            //create a filestream variable that streams in the html file
            FileInputStream inputFile = new FileInputStream(htmlFile);
        }
    }

    public void sendResponse() throws IOException, NoSuchAlgorithmException {
        boolean isWebSocket = request.headerInfo.containsKey("Sec-WebSocket-Key");

        //if the request is a websocket request, send back the right header info
        if (isWebSocket) {
            sendWebSocketHeader(); // Note: this routine never ends...
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
    public void sendWebSocketHeader() throws IOException, NoSuchAlgorithmException {
        String encodeKey = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1")
                .digest((request.headerInfo.get("Sec-WebSocket-Key") + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes("UTF-8")));
        //if we have a Web Socket request line from the header then we want to send the appropriate header information
        //send the appropriate header info
        outputHeader.print("HTTP/1.1 101 Switching Protocols\r\n");
        outputHeader.print("Upgrade: websocket\r\n");
        outputHeader.print("Connection: Upgrade\r\n");
        outputHeader.print("Sec-WebSocket-Accept: " + encodeKey + "\r\n");
        outputHeader.print("\r\n"); // send blank line / end of headers
        outputHeader.flush();

        System.out.println("Header was sent");


        // start talking binary over the ws with the client
        while(true) {
            DataInputStream in = new DataInputStream( request.requestFromClient ); // note we need the whole socket, not just the in stream
            byte b0 = in.readByte();
            byte b1 = in.readByte();

            int len = b1 & 0x7F;
            System.out.println("Got a msg from the client with len: " + len);
        }
    }

}



