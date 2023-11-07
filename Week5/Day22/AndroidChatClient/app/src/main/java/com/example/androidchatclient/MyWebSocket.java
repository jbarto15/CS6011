package com.example.androidchatclient;

import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class MyWebSocket extends WebSocketAdapter {

    final String MsTag = "MyWebSocket:JB";
    boolean wsOpen = false;


    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        super.onConnected(websocket, headers);
        wsOpen = true;
        ChatActivity.sendJoinMsg();
    }

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        super.onTextMessage(websocket, text);
        //message we will send to the listview
        String msg = null;

        String userName = text.split("\"user\":\"")[1];
        userName = userName.split("\"")[0];

        //store the text message as a JSON object
        JSONObject jsonObject = new JSONObject(text);

        //use this in the if checks depending on what type of message it is
        if (jsonObject.getString("type").equals("join")) {
            //add the message to the list view
            msg = userName + " has joined the room: " + jsonObject.getString("room");
        }
        else if (jsonObject.getString("type").equals("message")) {
            msg = userName + ": " + jsonObject.getString("message");
        }
        else if (jsonObject.getString("type").equals("leave")) {
            msg = userName + " has left the room";
        }

        ChatActivity.messages_.add(msg);

        //after adding the message we receive to the messages array list we want to post the messages
        //...to our list view widget
        ChatActivity.listView_.post( new Runnable() {
            @Override
            public void run() {
                ChatActivity.adapter_.notifyDataSetChanged();
                ChatActivity.listView_.smoothScrollToPosition( ChatActivity.adapter_.getCount() );
            }
        });

    }


    @Override
    public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
        super.onError(websocket, cause);
    }


}
