import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class webServer {
    public static void main(String[] args) throws IOException {
        //implement a server
        //create a server socket variable that takes in port 8080
        ServerSocket server = new ServerSocket(8080);

        //create a string variable that stores the path to the file that will be given to the client if their requested file is not found
        String errorFilePath = "/Users/joshbarton/Desktop/MSD2023/CS6011/Week1/Day4/MyHttpServer/resources/errorHtml.html";
        //file variable that stores a file
        File errorHtmlFile = new File(errorFilePath);
        //file input stream variable that inputs the error message html file
        FileInputStream inputErrorFile = new FileInputStream(errorHtmlFile);

        //while loop that allows the server socket to always be looking for a client request
        while (true) {
            //create socket variable that creates a socket on the server to be able to recognize the client
            Socket client = server.accept();
            //use the scanner class to more easily read in the data from the client
            Scanner scanner = new Scanner(client.getInputStream());

            //variable to store the request from the client
            String clientRequest = "";

            //check to see if there is input from the client
            if (scanner.hasNext()) {
                //store the client request in client request variable
                clientRequest = scanner.nextLine();
                System.out.println("Client Request: " + clientRequest);

                //parse the client request into parts based on where there is a space and store it into an array of strings
                String[] parts = clientRequest.split(" ");

                //create a string variable that stores the path to the file that will be given to the client
                String filePath = "/Users/joshbarton/Desktop/MSD2023/CS6011/Week1/Day4/MyHttpServer/resources" + parts[1];
                //create a file variable that creates a new File type and takes the filePath as its parameter
                File htmlFile = new File(filePath);


                //create output stream variable that assigns the clients output stream to an OutputStream variable
                OutputStream outputStream = client.getOutputStream();

                //check to make sure the request is a GET request and that the requested file exists
                if (parts[0].equals("GET") && htmlFile.exists()) {
                        //create a filestream variable that streams in the html file
                        FileInputStream inputFile = new FileInputStream(htmlFile);
                        //if file exists print out file found
                        System.out.println("File found");
                        //give the header information to the client
                        outputStream.write("HTTP/1.1 200 OK\n".getBytes());
                        outputStream.write("Content-Type: text/html\n".getBytes());
                        outputStream.write("\n".getBytes());
                        //using the transferTo method in the input FileInputStream class, transfer the requested file directly to the client output stream
                        inputFile.transferTo(outputStream);

                        //flush the output stream
                        outputStream.flush();

                        //close the output stream
                        outputStream.close();
                    }
                else if(parts[0].equals("GET") && !htmlFile.exists()){
                        //give the header information to the client and the file
                        outputStream.write("HTTP/1.1 404 Not Found".getBytes());
                        outputStream.write("Content-Type: text/html".getBytes());
                        outputStream.write("\n".getBytes());
                        outputStream.write("404 Not Found".getBytes());
                        inputErrorFile.transferTo(outputStream);

                        //flush the output stream
                        outputStream.flush();

                        //close the output stream
                        outputStream.close();
                    }
                }
            }
        }
    }
