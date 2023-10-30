import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class HTTPRequest {
    //input stream
    InputStream requestFromClient;

    //store the filename
    String filename;

    //variable to store the rest of the header information after the parsing
    public HashMap<String, String> headerInfo;


    //constructor that takes the client.getRequest
    public HTTPRequest(InputStream clientRequest) {
        requestFromClient = clientRequest;
        headerInfo = new HashMap<>();

    }

    //get the file name by scanning the client request, take the string and split it up and give back the filename
    public void getHeaderInfo() {
        //create scanner to scan the input stream
        Scanner scanner = new Scanner(requestFromClient);
        //variable to store the filename
        filename = "";
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
                // System.out.println("Line is empty!");
                break;
            }

            //parse the client request into parts based on where there is a space and store it into an array of strings
            String[] parts = line.split(": ");

            //store the first part of the request as the keyword for the map, and the second part as the values
            headerInfo.put(parts[0], parts[1]);
        }

        // Print keys and values
//        for (String i : headerInfo.keySet()) {
//            System.out.println("key: " + i + " value: " + headerInfo.get(i));
//        }

        //if the filename is empty, put a / before it so I don't go into my resources folder
        if (filename.isEmpty()) {
            filename = "/";
        }
    }


    //method that will get the name of the file
    public String getFileName() {
        return filename;
    }

}




