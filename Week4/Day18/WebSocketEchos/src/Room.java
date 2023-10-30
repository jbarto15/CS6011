import java.lang.reflect.Array;
import java.util.ArrayList;

public class Room {
    //an array list of ConnectionHandlers
    ArrayList<ConnectionHandler> allClients;

    //we need to keep track of all of the clients. We could do this by having an array list of all of the Sockets maybe?

    //list of rooms
    static ArrayList<Room> allRooms;

    //constructor
    public Room() {

    }

    //methods

    //add a client
    public void addAClient() {

    }

    //send message to all clients in the room
    public String sendMessage() {
        return null;
    }

    //remove a client
    public void removeClient() {

    }

    public String getTheRoom() {
        return "Name of the Room";
    }
}


