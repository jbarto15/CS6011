import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MyRunnable implements Runnable {
    Socket client_;

    MyRunnable(Socket client) {
        client_ = client;
    }
    @Override
    public void run() {
        InputStream clientReq = null;
        try {
            clientReq = client_.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream = null;
        try {
            outputStream = client_.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HTTPRequest request = new HTTPRequest(clientReq);
        String filename = request.getFileName(clientReq);
        System.out.println(filename);

        HTTPResponse response = null;
        try {
            response = new HTTPResponse(filename, outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            response.streamInFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            response.streamOutFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


