package com.example.androidchatclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    final String MsTag = "ChatActivity:JB";

    private static String message_;

    private static String roomName_;

    private static String userName_;

    private static final String WS_URL = "ws://10.0.2.2:8080";

    public static WebSocket ws;

    public static ArrayList<String> messages_ = new ArrayList<>();

    public static ListView listView_;

   public static ArrayAdapter adapter_;

   public static String time_;


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

            //set the room text view to the the room name
            room.setText(roomName_);

            Log.d(MsTag, "Room name is: " + roomName_);



            //get the list view widget
            listView_ = findViewById(R.id.listView);


            //create an adapter that will help the list view display the messages
            adapter_ = new ArrayAdapter(this, android.R.layout.simple_list_item_1, messages_);


            //attach the adapter to the list view
            listView_.setAdapter(adapter_);
        }


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

        //get the actual message and save it in a variable message
        message_ = message.getText().toString();

        Log.d(MsTag, message_);

        //send the message to the server
        //ws.sendText(" {\"type\":\"message\",\"user\":\"" + userName_ + "\",\"room\":\"" + roomName_ + "\",\"message\":\"" + message_ + "\"}");        //,\"time\":\"" + time_

        // Get the current time
        LocalDateTime currentTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentTime = LocalDateTime.now();
        }

        // Define a format for the time
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        }

        // Format the current time as a string
        String formattedTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formattedTime = currentTime.format(formatter);
        }

        // Include the formatted time in your message JSON
        String msg = String.format("{\"type\":\"message\",\"user\":\"%s\",\"room\":\"%s\",\"message\":\"%s\",\"time\":\"%s\"}",
                userName_, roomName_, message_, formattedTime);

        // Send the message
        ws.sendText(msg);
    }


    //method that sends the join msg to the server
    public static void sendJoinMsg() {
        //send the join message to the server
        ws.sendText(" {\"type\":\"join\",\"room\":\"" + roomName_ + "\",\"user\":\"" + userName_+"\"}");
    }


    //method that sends the leave msg to the server and takes the user to the main login page
    //when the leave button is clicked
    public void sendLeaveMsg(View view) {
        //send the leave message to the server
        ws.sendText(" {\"type\":\"leave\",\"room\":\"" + roomName_ + "\",\"user\":\"" + userName_ + "\",\"room\":\"" + roomName_ + "\"}");
        //take the user to the login page
        Intent intent1 = new Intent(ChatActivity.this, MainActivity.class);
        startActivity(intent1);
    }



}