import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    Socket client_;

    ConnectionHandler(Socket client) {
        client_ = client;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = client_.getInputStream();
            OutputStream outputStream = client_.getOutputStream();

            HTTPRequest request = new HTTPRequest(inputStream);
            request.getHeaderInfo();

            String filename = request.getFileName();
            System.out.println("found filename of: " + filename);

            HTTPResponse response = new HTTPResponse(filename, outputStream, request, client_);
            response.sendResponse();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}




