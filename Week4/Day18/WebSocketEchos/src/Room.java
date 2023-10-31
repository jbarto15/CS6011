import java.io.DataOutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {
    //an array list of ConnectionHandlers
    ArrayList<ConnectionHandler> allClients;

    String roomName_;

    //list of rooms
    static HashMap<String, Room> allRooms = new HashMap<>();

    //list of users
    static HashMap<String, Socket> allUsers = new HashMap<>();

    //constructor
    public Room(String nameOfRoom) {
        this.roomName_ = nameOfRoom;

    }



    //methods

    //add a client
    public synchronized void addAClient(String userName, Socket client) {
        allUsers.put(userName, client);
    }

    //send message to all clients in the room
    public synchronized void sendMessage(String message) {
        for (Map.Entry<String, Socket> entry : allUsers.entrySet()) {
            Socket socket = entry.getValue();
            //some object or if you have method call method
            //object.sendMessage(message);
            try {
                DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
                //send the first byte of the header
                dataOut.writeByte(0x81);
                //send the length of the message
                dataOut.writeByte(message.length());  //something is going on with this line
                //send the message
                dataOut.writeBytes(message);
            } catch (Exception e) {
                System.out.println("Error");
            }

        }
    }

    //remove a client
    public synchronized void removeClient(String userName, Socket client) {
        allUsers.remove(userName, client);
    }

    public synchronized static Room getRoom(String name) {
        //variable that will store the name of the room if it doesn't already exist
        Room newRoom;
        Room doesExist = new Room(name);
        //If room already exists, return it.
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





//            Room roomExists = allRooms.get(name); //"Room" Room : Test
//            if (roomExists == null) {
//                room1 = new Room(name);
//                allRooms.put(room1);
//            }
//        }


