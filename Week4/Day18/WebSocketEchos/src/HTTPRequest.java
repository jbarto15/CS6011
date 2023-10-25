import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class HTTPRequest {
    //input stream
    InputStream requestFromClient;

    //constructor that takes the client.getRequest
    public HTTPRequest(InputStream clientRequest) {
        requestFromClient = clientRequest;
    }

    //get the file name by scanning the client request, take the string and split it up and give back the filename
    public String getFileName(InputStream requestFromClient) {
        //create scanner to scan the input stream
        Scanner scanner = new Scanner(requestFromClient);
        //variable to store the filename
        String filename = "";
        if (scanner.hasNext()) {
            //store the client request in client request variable
            String clientRequest = scanner.nextLine();
            System.out.println("Client Request: " + clientRequest);

            //parse the client request into parts based on where there is a space and store it into an array of strings
            String[] parts = clientRequest.split(" ");
            filename = parts[1];
            //if the only a / is given from the client, assign that slash to /index.html
            if (parts[0].equals("GET") && parts[1].equals("/")) {
                parts[1] = "/index.html";
                filename = parts[1];
            }
        }
        return filename;
    }
}

