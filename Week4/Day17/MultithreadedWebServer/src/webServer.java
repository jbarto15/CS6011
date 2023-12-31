import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class webServer {
    public static void main(String[] args) {
        try {
            //implement a server
            //create a server socket variable that takes in port 8080
            ServerSocket server = new ServerSocket(8080);

            //while loop that allows the server socket to always be looking for a client request
            while (true) {
                //create socket variable that creates a socket on the server to be able to recognize the client
                Socket client = server.accept();
                Thread thread = new Thread(new MyRunnable(client));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}