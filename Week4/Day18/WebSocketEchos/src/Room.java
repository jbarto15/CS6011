import netscape.javascript.JSObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/*This is the room class which allows us to create a room object that stores the information
 * for the rooms created by the client. Each client will join a room and in the room they are
 * able to send messages to other users in the same room. The room class is also able to add
 * or remove clients from the room */

public class Room {
    //store the room name that is given to us through the constructor
    private String roomName_;

    //list of rooms stored in a Hash Map
    static HashMap<String, Room> allRooms = new HashMap<>();

    //list of users stored in a Hash Map, storing the name of the user as the key and the
    //...value is the socket information
    static HashMap<String, Socket> allUsers = new HashMap<>();

    private ArrayList<String> messages;


    //constructor
    private Room(String nameOfRoom) {
        //store the name of the room given to my member variable roomName_
        this.roomName_ = nameOfRoom;
        this.messages = new ArrayList<>();
    }



    //METHODS

    //add a client to the room. It takes a string and a socket as parameter and doesn't return anything
    public synchronized void addAClient(String userName, Socket client, String msg) {
//        String message = "";
//        // 'client' is about to join the room...
//        // send to client a message for everyone already in the room
//        for (Map.Entry<String, Socket> entry : allUsers.entrySet()) {
//            //create a socket that stores the value of client socket
//            //Socket socket = entry.getValue();
//            String name = entry.getKey();
//            userName = name;
//            //message = "{type: join, room: " + roomName_ + ", user: " + userName + "}";
//
//            message = " {\"type\":\"join\",\"room\":\"" + roomName_ + "\",\"user\":\"" + userName+"\"}";
//
//
//            System.out.println("Join Message: " + msg);
//
//            // send msg out through socket...
//            try {
//                //data output stream that will stream out the message in bytes
//                DataOutputStream dataOut = new DataOutputStream(client.getOutputStream());
//                //send the first byte of the header
//                dataOut.writeByte(0x81);
//                //send the length of the message
//                dataOut.writeByte(message.length()); // <- not correct for len > 125
//                //send the message
//                dataOut.writeBytes(message);
//
//            } catch (Exception e) {
//                System.out.println("Error");
//            }
//        }

        //add the new client to my map of all users using the put method
        allUsers.put( userName, client );
        //sendMessage( msg );

    }

    //send message to all clients in the room. It takes a string and doesn't return anything
    public synchronized void sendMessage(String message) {
        //loop through my map of all users
        for (Map.Entry<String, Socket> entry : allUsers.entrySet()) {
            //create a socket that stores the value of client socket
            Socket socket = entry.getValue();

            try {
                //data output stream that will stream out the message in bytes
                DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
                //send the first byte of the header
                dataOut.writeByte(0x81);
                //send the length of the message
                dataOut.writeByte(message.length()); // <- not correct for len > 125
                //send the message
                dataOut.writeBytes(message);

            } catch (Exception e) {
                System.out.println("Error");
            }
        }
    }

    //remove a client from a room. Takes a string and a socket and returns nothing
    public synchronized void removeClient(String userName, Socket client) {
        //remove the user from the all users map my using the remove method and removing
        //...the username and its socket
        allUsers.remove(userName, client);
    }

    /*the getRoom method takes in a string which is the name of the room, checks to see if that room
    already exists, if so, it returns the room that already it exists, if not, it creates a new
    room and returns that new room*/
    public synchronized static Room getRoom(String name) {
        //variable that will store the name of the room if it doesn't already exist
        Room newRoom;
        //room object that stores the name of the room given to us
        Room doesExist = new Room(name);
        //check my map of all rooms and if the room already exists, return that room
        if (allRooms.containsValue(doesExist)) {
            return doesExist;
        }
        else {
            //create the room by assigning it to the nameOfRoom variable
            newRoom = new Room(name);
            //add it to the list of rooms
            allRooms.put(name, newRoom);
        }
        //return the new room
        return newRoom;
    }


    //method that creates a data output stream and sends a message
    public void send(String msg, Socket client) throws IOException {
        //data output stream that will stream out the message in bytes
        DataOutputStream dataOut = new DataOutputStream(client.getOutputStream());
        //send the first byte of the header
        dataOut.writeByte(0x81);
        //send the length of the message
        dataOut.writeByte(msg.length()); // <- not correct for len > 125
        //send the message
        dataOut.writeBytes(msg);
    }
}


/*String message = "";
        // 'client' is about to join the room...
        // send to client a message for everyone already in the room
        for (Map.Entry<String, Socket> entry : allUsers.entrySet()) {
            //create a socket that stores the value of client socket
            //Socket socket = entry.getValue();
            String name = entry.getKey();
            userName = name;
            //message = "{type: join, room: " + roomName_ + ", user: " + userName + "}";

            System.out.println("Join Message: " + msg);

            //parse the msg string
            String user = msg.split("\"user\":\"")[1].split("\"")[0];
            System.out.println("user: " + user);
            //save the 5th part of the split as the replacement

            //replace the user with the new user
            String master = msg;
            String target = user; //whatever variable stores the user from the parsed message aka the 5th part of the message
            String replacement = userName;
            String processed = master.replace(target, replacement);

            System.out.println("Processed: " + processed);

            // send msg out through socket...
            try {
                //data output stream that will stream out the message in bytes
                DataOutputStream dataOut = new DataOutputStream(client.getOutputStream());
                //send the first byte of the header
                dataOut.writeByte(0x81);
                //send the length of the message
                dataOut.writeByte(msg.length()); // <- not correct for len > 125
                //send the message
                dataOut.writeBytes(msg);

            } catch (Exception e) {
                System.out.println("Error");
            }
        }

        //add the new client to my map of all users using the put method
        allUsers.put( userName, client );
        sendMessage( msg ); */
