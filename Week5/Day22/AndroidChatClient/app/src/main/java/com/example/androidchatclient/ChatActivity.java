package com.example.androidchatclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;

public class ChatActivity extends AppCompatActivity {

    final String MsTag = "ChatActivity:JB";

    private static String message_;

    private static String roomName_;

    private static String userName_;

    private static final String WS_URL = "ws://10.0.2.2:8080/endpoint";

    private static WebSocket ws;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //set the displayRoom widget text to be the text of the room name
        TextView room = findViewById(R.id.displayRoom);

        //Bundle object that allows me to get the information from the login screen
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            //assign room name member variable to the room name the user input
            roomName_ = extras.getString("Room");
            userName_ = extras.getString("Username");
        }
        //set the room text view to the the room name
        room.setText(roomName_);

        Log.d(MsTag, "Room name is: " + roomName_);


        WebSocket ws = null;
        try {
            ws = new WebSocketFactory().createSocket(WS_URL);
            //listen for event and will use this class to implement them
            ws.addListener(new MyWebSocket());
            ws.connectAsynchronously();

        } catch (IOException e) {
            //AlertDialog alert = new AlertDialog("Server failed");
            Log.d(MsTag, "some error");
        }

    }


    //method to handle send button
    public void handleSendMsg(View view) {
        //log a message to the Logcat when the button is pressed
        Log.d( MsTag, "Send was pressed...");

        //get the text area that message is in
        EditText message = findViewById(R.id.messageText);

        message_ = String.valueOf(message.getText());

        Log.d(MsTag, message_);

        //send the message to the server
        ws.sendText(" {\"type\":\"message\",\"user\":\"" + userName_ + "\",\"room\":\"" + roomName_ + "\",\"message\":\"" + message_ + "\"}");

    }


    public void handleJoinMsg() {

    }

    public void handleLeaveMsg() {

    }

    public static void sendJoinMsg() {
        //send the join message to the server
        ws.sendText(" {\"type\":\"join\",\"room\":\"" + roomName_ + "\",\"user\":\"" + userName_ + "\",\"room\":\"" + roomName_ + "\"}");
    }


    public static void sendMsg(View view) {
        //log a message to the Logcat when the button is pressed
        //Log.d( MsTag, "Send was pressed...");

        //get the text area that message is in
        EditText message = findViewById(R.id.messageText);

        message_ = String.valueOf(message.getText());

        //Log.d(MsTag, message_);

        //send the message to the server
        ws.sendText(" {\"type\":\"message\",\"user\":\"" + userName_ + "\",\"room\":\"" + roomName_ + "\",\"message\":\"" + message_ + "\"}");

    }

    public static void sendLeaveMsg() {
        //send the leave message to the server
        ws.sendText(" {\"type\":\"leave\",\"room\":\"" + roomName_ + "\",\"user\":\"" + userName_ + "\",\"room\":\"" + roomName_ + "\"}");
    }





}


/*Methods I'll want for MyWebSocket class
* public class MyWebSocket extends WebSocketAdapter {
*   @Override
*   public void onMessage( String msg ) {
*       //put "msg" into the list view for display
*   }
*
*   @Override
*   public void onError( ) {
*
*   }
*
*   @Override
*   public void onConnect( ) {
*
*   }
*
*   @Override
*   public void //one more function that I can't remember
*  */