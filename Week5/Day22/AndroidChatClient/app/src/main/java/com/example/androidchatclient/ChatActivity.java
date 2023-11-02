package com.example.androidchatclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ChatActivity extends AppCompatActivity {

    final String MsTag = "ChatActivity:JB";

    private String message_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }


    //method to handle send button
    public void handleSendMsg(View view) {
        //log a message to the Logcat when the button is pressed
        Log.d( MsTag, "Send was pressed...");

        //get the text area that message is in
        EditText message = findViewById(R.id.messageText);

        message_ = String.valueOf(message.getText());

        Log.d(MsTag, message_);
    }




}