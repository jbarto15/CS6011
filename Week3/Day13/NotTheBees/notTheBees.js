
"use strict";

//get the canvas element
let canvas = document.getElementById("canvasDrawing");

//get the 2D rendering context of the canvas
let context = canvas.getContext("2d");

//create an image object for the snowflake image
let snowflakeImage = new Image();
//set the source of the snowflake image
snowflakeImage.src = "images/snowflake.jpg";

//create a image object for skier image
let skierImage = new Image();
//set the source of the skier image
skierImage.src = "images/skier.jpeg";

//create an empty array that will hold skier images so the skiers can chase the fresh pow
let skierArray = [];

//create a loop to add a bunch of skier images to the skierArray
for (let i = 0; i < 10; i++) {
    skierArray.push(skierImage);
}

//variables for the inital image position and size
//set intital x position of snowflake image
let imageX = canvas.width / 2;
//set initial y position of snowflake image
let imageY = canvas.height / 2;
//set the size of the image to 50 pixels. Will be used in the context.drawImage method to set the height and width of image
let imageSize = 50;

//load the image and draw it on the canvas
window.onload = function() {
    //add a mousemove event listener to the canvas so that the image will be able to update as the mouse moves
    canvas.addEventListener("mousemove", handleMouseMove);

    //draw the image on the canvas using the drawImage method, pass the snowflake image as a parameter
    drawImage(snowflakeImage);
};

//define a function to handle the snowflake image position when the mouse is moving, takes an event as a parameter
function handleMouseMove(event) {
    //variable that stores the X position of the mouse in the canvas
    let mouseX = event.clientX;
    //variable that stores the Y position of the mouse in the canvas
    let mouseY = event.clientY;

    //update the image position to follow the mouse position by assigning image x and y to the mouse x and y
    imageX = mouseX;
    imageY = mouseY;

    //redraw the canvas with the updated image position using the drawImage function
    drawImage(snowflakeImage);
}

//function to draw the image on the canvas, takes an image as a parameter
function drawImage(image) {
    //clear the entire canvas using the clearRect method. Starts the clear at position 0,0 and goes through the whole canvas width and height
    context.clearRect(0, 0, canvas.width, canvas.height);

    //draw the image at the new position using the drawImage method. Takes in the image, the image x and y coordinates as well as the image width and height
    context.drawImage(image, imageX, imageY, imageSize, imageSize);
}
