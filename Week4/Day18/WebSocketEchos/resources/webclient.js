

function mainFunction() {
    //create a web socket
    let ws  = new WebSocket("ws://localhost:8080");
    ws.onopen = handleConnect;
    let wsOpen = false;
    ws.onclose = handleClose;
    ws.onerror = handleError;
    ws.onmessage = handleMessage;
    //ws.close(); // When we are completely done.

    //variables to store the username, room, and message
    let usernameInput = document.getElementById("usernameValue");
    let roomInput = document.getElementById("roomValue");
    let messageInput = document.getElementById("inputMessage");
    usernameInput.addEventListener("keypress", handleUserNameAndRoom);
    roomInput.addEventListener("keypress", handleUserNameAndRoom);
    messageInput.addEventListener("keypress", handleSendMessage);

    //get the div section for "people in room"
    let peopleInRoom = document.getElementById("peopleInRoom");
    //get the div section for "message center"
    let messageCenter = document.getElementById("messageCenter");


    //get the join button
    let joinButton = document.getElementById("join");
    joinButton.onclick = function (event) {
        let roomNameValid = true;
        let room = roomInput.value;
        for (let i = 0; i < room.length; i++) {
            if (room[i] < 'a' || room[i] > 'z') {
                roomNameValid = false;
            }
        }

        if (!roomNameValid)
            alert("Room must be all lowercase letter.");


        if (roomNameValid) {
            let username = usernameInput.value;
            let message = messageInput.value;
            //if the websocket is open, send the username and room to the server
            if (wsOpen) {
                //create JSON object for the join
                let jsonJoin = {
                    "type" : "join",
                    "room" : roomInput.value,
                    "user" : usernameInput.value,
            }
                //send the JSON join
                ws.send(JSON.stringify(jsonJoin));

                //send username and room
                //ws.send("join " + username + " " + room);
            } else {
                message.value = "Could not open the websocket!";
            }
        }
    }

    //get the send button
    let sendButton = document.getElementById("sendButton");
    sendButton.onclick = function (event) {
        let message = messageInput.value;
        //if the websocket is open, send the message to the server
        if (wsOpen) {
            let jsonMessage = {
                "type"    : "message",
                "user"    : usernameInput.value,
                "room"    : roomInput.value,
                "message" : message
            }
            ws.send(JSON.stringify(jsonMessage));

            //send the message
            //ws.send("message " + message);

        } else {
            message.value = "Could not open the websocket!";
        }
    }

    //get the leave button
    let leaveButton = document.getElementById("leaveButton");
    leaveButton.onclick = function (event) {
        let message = messageInput.value;
        //if the websocket is open, send the message to the server
        if (wsOpen) {
            //create a leave JSON object
            let jsonLeave = {
                "type" : "leave",
                "room" : roomInput.value,
                "user" : usernameInput.value,
            }
            //send the JSON join
            ws.send(JSON.stringify(jsonLeave));

            //send the leave
            //ws.send("leave");
        } else {
            message.value = "Could not open the websocket!";
        }
    }



    //function that gets the username and room and stores them in variables, then sends the information to the server
    function handleUserNameAndRoom(event) {
        if (event.code === "Enter") {
            let roomNameValid = true;
            let room = roomInput.value;

            for (let i = 0; i < room.length; i++) {
                console.log(room[i]);
                if (room[i] < 'a' || room[i] > 'z') {
                    roomNameValid = false;
                }
            }

            if (!roomNameValid) {
                alert("Room must be all lowercase letter.");
            }


            if (roomNameValid) {
                let username = usernameInput.value;
                let message = messageInput.value;
                //if the websocket is open, send the username and room to the server
                if (wsOpen) {
                    //create JSON object for the join
                    let jsonJoin = {
                        "type" : "join",
                        "room" : roomInput.value,
                        "user" : usernameInput.value,
                    }
                    //send the JSON join
                    ws.send(JSON.stringify(jsonJoin));

                    //send username and room
                    //ws.send("join " + username + " " + room);
                } else {
                    message.value = "Could not open the websocket!";
                }
            }
        }
    }


    //function that gets the message and stores them in variables, then sends the information to the server
    function handleSendMessage(event) {
        if (event.code === "Enter") {
            let message = messageInput.value;
            let currentDate = new Date();
            let time = currentDate.getHours() + ":"
                + currentDate.getMinutes() + ":" + currentDate.getSeconds();
            //if the websocket is open, send the username and room to the server
            if (wsOpen) {
                let jsonMessage = {
                    "type"    : "message",
                    "user"    : usernameInput.value,
                    "room"    : roomInput.value,
                    "message" : message,
                    "time"    : time
                }
                ws.send(JSON.stringify(jsonMessage));


                //send the message
                //ws.send("message " + message);
            } else {
                message.value = "Could not open the websocket!";
            }
        }
    }


    //function that will handle the connection of the client to the server
    function handleConnect(event) {
        //ws.send("Hello");
        wsOpen = true;
    }

    //function that will handle closing the connection
    function handleClose(event) {
        alert("Connection is closed...");
    }

    //function that will handle any errors
    function handleError(event) {

    }

    //function that will handle messages sent
    function handleMessage(event) {
        let receivedMessage = event.data;
        console.log("received message: " + receivedMessage);
        //alert("Message is received...");

        //parse the JSON object
        let object = JSON.parse(receivedMessage);

        //get the room and the message
        let user = object.user;
        let room = object.room;
        let message = object.message;
        let time = object.time;

        console.log(user);
        console.log(room);

        //create elements that will add text to the room and message center
        let addToRoom = document.createElement("p");
        let addToMessageCenter = document.createElement("p");
        let addTime = document.createElement("p");


        if (object.type === "join") {
            //add the user to the division "People in Room"
            addToRoom.innerHTML = user + " has joined the room: " + room;
            //add the paragraph to the "People in Room division"
            peopleInRoom.appendChild(addToRoom);
        }

        if (object.type === "message" && object.user !== "null") {
            //add the message to the division "message center"
            addToMessageCenter.innerHTML = user + ": " + message;
            addTime.innerHTML = "time: " + time;
            messageCenter.appendChild(addToMessageCenter);
            messageCenter.appendChild(addTime);
        }

        if (object.type === "leave") {
            let leaveMessage = document.createElement("p");
            leaveMessage.innerHTML = user + " left the room."
            peopleInRoom.appendChild(leaveMessage);
        }
    }
}

window.onload = mainFunction();


//anywhere I have a ws.send I need to alter that send message and create a JSON object for that.
// Once I have the JSON object I need to do ws.send(JSON.stringify(myJSONobject)
