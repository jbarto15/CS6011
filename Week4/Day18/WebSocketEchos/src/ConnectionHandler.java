import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    //member variable that stores the socket information of the client
    Socket client_;

    ConnectionHandler(Socket client) {
        //assign the client given to us to our client member variable
        client_ = client;
    }

    @Override
    public void run() {
        try {
            //input stream variable that stores the input stream from the client
            InputStream inputStream = client_.getInputStream();
            //output stream variable that stores the output stream from the client
            OutputStream outputStream = client_.getOutputStream();

            //create HTTPRequest variable that takes in the client input stream
            HTTPRequest request = new HTTPRequest(inputStream);
            //get the header information from the request and save the information in the
            //...appropriate HTTPRequest variables
            request.getHeaderInfo();

            //store the filename from the request
            String filename = request.getFileName();
            System.out.println("found filename of: " + filename);

            //HTTPResponse variable that is sent the filename, outputStream, request object, and the client socket
            HTTPResponse response = new HTTPResponse(filename, outputStream, request, client_);
            //send the appropriate response to the client
            response.sendResponse();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}




