"use strict";

//This is the command to run in the terminal
//java -jar SimpleCalcServer.jar -v

//localhost:8080/calculate?x=20&y=20   //this is the request that is given to the server

//variables to store the x, y, and result values
const xValue = document.getElementById("xValue");
const yValue = document.getElementById("yValue");
const resultInput = document.getElementById("result");

//no button - we might create it later on

//I will listen to key press event on xValue and yValue
xValue.addEventListener("keypress",handleKeyPress)
yValue.addEventListener("keypress", handleKeyPress);

//Option 2 - Web Sockets
let ws = new WebSocket("ws://localhost:8080/calculate?");
let wsOpen = false;

ws.onopen = function () {
    console.log("Open Connection")
    wsOpen = true;
} // handle something

ws.onmessage = function (event) {
    resultInput.value = event.data; //data coming from the socket
} //handle something



function handleKeyPress(event) {
    if (event.code == "Enter" || event.code == "click") {
        let x = parseFloat(xValue.value);
        let y = parseFloat(yValue.value);

        if (isNaN(x)) {
            alert("X should be a number!");
            xValue.value = "Enter a number: ";
            xValue.select();
            return;
        }

        if (isNaN(y)) {
            alert("Y should be a number!");
            yValue.value = "Enter a number: ";
            yValue.select();
            return;
        }

        //testing if x and y are working properly and displayed in the result
        //locally without going to the server - no AJAX - no web sockets
        //Naive way (line below)
        //resultInput.value = (x+y);


        //Option 1 - using AJAX
        // let xhr = new XMLHttpRequest();
        // xhr.open ("GET", "http://localhost:8080/calculate?x=" + x + "&y=" + y);
        // xhr.onerror = handleError;
        // xhr.onload = handleAjax;
        // xhr.send();




        if (wsOpen) {
            //send x and y
            ws.send(x + " " + y);
        } else {
            resultInput.value = "Could not open the websocket!";
        }




        console.log("x value", x);

        //resultInput.value = (x + y);

    }
}


function handleError() {
    resultInput.value = "Problem connecting to the server";
}

function handleAjax() {
    resultInput.value = this.responseText;
}