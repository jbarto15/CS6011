package com.example.androidchatclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final String MsTag = "MainActivity:JB";

    String username_;

    String roomName_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void handleJoin(View view) {
        //log a message to the Logcat when the button is pressed
        Log.d( MsTag, "Button was pressed...");

        //get the text area that username is in
        EditText username = findViewById(R.id.usernameText);
        //get the text area that room name is in
        EditText room = findViewById(R.id.roomText);

        username_ = String.valueOf(username.getText());
        roomName_ = String.valueOf(room.getText());

        Log.d(MsTag, username_);
        Log.d(MsTag, roomName_);

        //if the username and roomname are not empty then go to the next activity
        if (!username_.isEmpty() && !roomName_.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            intent.putExtra("Username", username_);
            intent.putExtra("Room", roomName_);
            startActivity(intent);
        }

        else {
            //have a message alert that tells the user to enter both a username and a room name
            Log.d(MsTag, "Please enter a username and room name");
        }
    }


}





//    Create (at least) two activities for your chat application (including the login page, and the page that will contain chat messages).
//
//    Design your layouts using the UI designer (as you did in the Lab).
//
//    The views (UI Widgets) that will be needed on the Login and Chat Pages will be similar to those used in your web page client.
//
//    Write the code necessary to switch from the Login Activity to your Chatroom Activity, displaying the room name at the top.