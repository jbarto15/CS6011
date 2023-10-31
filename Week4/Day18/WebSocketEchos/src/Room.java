import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    //constructor
    private Room(String nameOfRoom) {
        //store the name of the room given to my member variable roomName_
        this.roomName_ = nameOfRoom;
    }



    //METHODS

    //add a client to the room. It takes a string and a socket as parameter and doesn't return anything
    public synchronized void addAClient(String userName, Socket client) {
        //add the new client to my map of all users using the put method
        allUsers.put(userName, client);
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
                dataOut.writeByte(message.length());
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
}


