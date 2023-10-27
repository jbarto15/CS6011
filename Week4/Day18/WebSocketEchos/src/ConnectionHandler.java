import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class ConnectionHandler implements Runnable {
    Socket client_;

    ConnectionHandler(Socket client) {
        client_ = client;
    }
    @Override
    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = client_.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream = null;
        try {
            outputStream = client_.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HTTPRequest request = new HTTPRequest(inputStream);
        String filename = request.getFileName();
        System.out.println("found filename of: " + filename);
        //request.parse(); // getFileName() now does this

        HTTPResponse response = null;
        try {
            response = new HTTPResponse(filename, outputStream, request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            response.streamInFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            response.sendResponse();
        } catch (IOException e) {
            //throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            //throw new RuntimeException(e);
        }

    }
}


