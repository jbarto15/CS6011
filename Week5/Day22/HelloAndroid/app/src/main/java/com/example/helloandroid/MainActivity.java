package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final String MsTag = "MainActivity:JB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleButton(View view) {
        //log a message to the Logcat when the button is pressed
        Log.d( MsTag, "Button was pressed...");

        //get the text area that hello world is printed in
        TextView textView = findViewById(R.id.outputInfoId);

        //set the text to Go Jazz instead of Hello World when the button is pressed
        textView.setText("Go Jazz!");
    }

}