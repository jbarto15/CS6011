import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class webServer {
        public static void main(String[] args) throws IOException {
            //When a request comes in, parse the client's request, and (assuming it's well formed) attempt to send the requested file from the resources folder of your project. If the file doesn't exist, send back a 404 error

            //implement a server
            //create a server socket variable that takes in port 8080
            ServerSocket server = new ServerSocket(8080);

            //while loop that allows the server socket to always be looking for a client request
            while (true) {
                //create socket variable that creates a socket on the server to be able to recognize the client
                Socket client = server.accept();
                //use the scanner class to more easily read in the data
                Scanner scanner = new Scanner(client.getInputStream());

                //variable to store the request from the client
                String clientRequest = "";

                //check to see if there is input from the client, if so, print out there request
                if (scanner.hasNext()) {
                    //store the client request in a string variable
                    clientRequest = scanner.nextLine();
                    System.out.println("Client Request: " + clientRequest);

                    //parse the client request into its parts and store it into an array of strings
                    String[] parts = clientRequest.split(" ");

                    //store the file that is being requested in a variable
                    System.out.println(parts[1]);

                    //create a file variable that stores the path to the file that will be given to the client
                    String filePath = "/Users/joshbarton/Desktop/MSD2023/CS6011/Week1/Day4/MyHttpServer/resources" + parts[1];
                    File htmlFile = new File(filePath);
                    FileInputStream inputFile = new FileInputStream(htmlFile);

                    //create output stream variable that assigns the clients output stream to an OutputStream variable
                    OutputStream outputStream = client.getOutputStream();

                    //check to make sure the request has all 3 parts of getting a request from client by creating individual variables for them
                    if (parts[0].equals("GET")) {
                    //if (parts[0].equals("GET") && parts[1].equals(filePath) || parts[1].equals("/") && parts[2].equals( "HTTP/1.1")) {
                        //return to the client that the specific file is found
                        if (htmlFile.exists()) {
                            System.out.println("File found");
                            //give the header information to the client and the file
                            outputStream.write("HTTP/1.1 200 OK\n".getBytes());
                            outputStream.write("Content-Type: text/html\n".getBytes());
                            outputStream.write("\n".getBytes());
                            inputFile.transferTo(outputStream);

                            //flush the output stream
                            outputStream.flush();

                            //close the output stream
                            outputStream.close();
                        }
                    } else if (!htmlFile.exists()) {
                        //return the error that the file is not found
                        //create a file variable that stores the path to the file that will be given to the client
                        String errorFilePath = "/Users/joshbarton/Desktop/MSD2023/CS6011/Week1/Day4/MyHttpServer/resources/errorHtml.html";
                        File errorHtmlFile = new File(errorFilePath);
                        FileInputStream inputErrorFile = new FileInputStream(errorHtmlFile);

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
