import javax.lang.model.util.SimpleAnnotationValueVisitor6;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class HTTPRequest {
    //input stream
    InputStream requestFromClient;

    //variable to store the rest of the header information after the parsing
    public HashMap<String, String> headerInfo;

    //variable to store the magic string
    public String magicString;

    //constructor that takes the client.getRequest
    public HTTPRequest(InputStream clientRequest) {
        requestFromClient = clientRequest;
        headerInfo = new HashMap<>();
    }

    //get the file name by scanning the client request, take the string and split it up and give back the filename
    public String getFileName() {
        //create scanner to scan the input stream
        Scanner scanner = new Scanner(requestFromClient);
        //variable to store the filename
        String filename = "";
        if (scanner.hasNext()) {
            //store the client request in client request variable
            String clientRequest = scanner.nextLine();

            //parse the client request into parts based on where there is a space and store it into an array of strings
            String[] parts = clientRequest.split(" ");
            filename = parts[1];
            //if the only a / is given from the client, assign that slash to /index.html
            if (parts[0].equals("GET") && parts[1].equals("/")) {
                parts[1] = "/webclient.html";
                filename = parts[1];
            }
        }

        while (true) {
            String line = scanner.nextLine();
            //System.out.println("Read line: " + line);
            if (line.isEmpty()) {
                System.out.println("Line is empty!");
                break;
            }

            //parse the client request into parts based on where there is a space and store it into an array of strings
            String[] parts = line.split(":");

            //store the first part of the request as the keyword for the map, and the second part as the values
            headerInfo.put(parts[0], parts[1]);
        }

        // Print keys and values
        for (String i : headerInfo.keySet()) {
            System.out.println("key: " + i + " value: " + headerInfo.get(i));
        }

        // System.out.println( "headers are: " + headerInfo);
        if (filename.equals("")) {
            filename = "/";
        }
        return filename;
    }
}






//        GET /chat HTTP/1.1
//        Host: example.com:8000
//        Upgrade: websocket
//        Connection: Upgrade
//        Sec-WebSocket-Key: dG...ZQ==
//        Sec-WebSocket-Version: 13
//





