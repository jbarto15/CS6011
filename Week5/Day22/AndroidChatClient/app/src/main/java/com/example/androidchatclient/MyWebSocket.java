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
            msg = userName + " has joined the room";
        }
        else if (jsonObject.getString("type").equals("message")) {
            msg = userName + " :" + jsonObject.getString("message");
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







        //PSUEDO CODE
        //1. get the received message and store it in a variable that we will use later on in the code
        //2. The object we received I think will be a JSON object so we want to parse the object
        //and store the user, room, message, and time information in a JSON object
        //3. Add the

        //create elements that will add text to the room and message center
//        let addToRoom = document.createElement("p");
//        let addToMessageCenter = document.createElement("p");
//        let addTimeToMessageCenter = document.createElement("p");
//
//
//        if (object.type === "join") {
//            //add the user to the division "People in Room"
//            addToRoom.innerHTML = user + " has joined the room: " + room;
//            //add the paragraph to the "People in Room division"
//            peopleInRoom.appendChild(addToRoom);
//        }
//
//        if (object.type === "message" && object.user !== "null") {
//            //add the message to the division "message center"
//            addToMessageCenter.innerHTML = user + ": " + message;
//            addTimeToMessageCenter.innerHTML = time;
//            messageCenter.appendChild(addToMessageCenter);
//            messageCenter.appendChild(addTimeToMessageCenter);
//        }
//
//        if (object.type === "leave") {
//            let leaveMessage = document.createElement("p");
//            leaveMessage.innerHTML = user + " left the room."
//            peopleInRoom.appendChild(leaveMessage);
//        }
    }


    @Override
    public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
        super.onError(websocket, cause);
    }


}
