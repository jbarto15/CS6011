
"use strict";

//get the canvas element
let canvas = document.getElementById("canvasDrawing");

//get the 2D rendering context of the canvas
let context = canvas.getContext("2d");

//assign the canvas width and height to be the size of the window width and height
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

//store the canvas width and height in variables
let canvasWidth = canvas.width;
let canvasHeight = canvas.height;

//create an image object for the snowflake image
let snowflake = new Image();
//set the source of the snowflake image
snowflake.src = "images/snowflake.jpg";
//set the x and y position of the snowflake
snowflake.xPos = (canvasWidth / 2);
snowflake.yPos = (canvasHeight / 2);
//set the height and width of snowflake
snowflake.width = 50;
snowflake.height = 50;

//create an array of skier images using the generate skier function
let skierArray = [];
for (let i = 0; i < 4; i++) {
    generateSkier();
}

//background image
let background = new Image();
background.src = "images/skiscene.png";


function generateSkier() {
    //create a image object for skier image
    let skier = new Image();
    //set the source of the skier image
    skier.src = "images/skier.png";
    skier.width = 100;
    skier.height = 100;
    skier.xPos = Math.floor(Math.random() * (canvas.width));
    skier.yPos = Math.floor(Math.random() * (canvas.height));
    skierArray.push(skier);
}

//create an animate function
function animate() {
    //function that will erase everything on the canvas
    eraseOld();
    //draw the snowflake
    context.drawImage(snowflake, snowflake.xPos, snowflake.yPos, snowflake.width, snowflake.height);
    //draw the skiers
    for (let i = 0; i < skierArray.length; i++) {
        context.drawImage(skierArray[i], skierArray[i].xPos, skierArray[i].yPos, skierArray[i].width, skierArray[i].height);
        // updateSkierXandYValues();
        //conditions that check the movement of the images
        // if (moving) {
        //     skierArray[i].xPos += Math.floor(Math.random() * (8 - 3) + 3);
        //     skierArray[i].yPos += Math.floor(Math.random() * (8 - 3) + 3);
        // }
        moveSkier(skierArray[i], snowflake);
        checkCollision(skierArray[i]);
    }

    //pass the animate function into the request animation frame so that it will animate the canvas continually
    window.requestAnimationFrame(animate);
}

//create an erase function that wipes the screen after each animation
function eraseOld() {
    //add new canvas to create illusion of deletion of previous canvas
    context.fillStyle = "#FFFFFF";
    context.fillRect(0, 0, canvasWidth, canvasHeight);
    context.drawImage(background, 0, 0, canvasWidth, canvasHeight);
}


//define a function to handle the snowflake image position when the mouse is moving, takes an event as a parameter
function handleMouseMove(e) {
    //update the image position to follow the mouse position by assigning image x and y to the mouse x and y
    snowflake.xPos = e.x - (snowflake.width / 2);
    snowflake.yPos = e.y - (snowflake.height / 2);
}


//function to check collisions between the skier and the snowflake
function checkCollision(skier) {
    //variables to store the distance between the snowflake and the skier
    let distX = snowflake.xPos - skier.xPos;
    let distY = snowflake.yPos - skier.yPos;

    //check if the distance is less than 10, if so, call the restart function
    if (distX < 10 && distY < 10) {
        //gameOver(snowflake);
    }
}


//function to move the skiers closer to the mouse
function moveSkier(skier, snowflake) {
    //speed variable
    let speed = Math.floor(Math.random() * (6 - 2) + 2);;

    if (snowflake.xPos < skier.xPos) {
        skier.xPos -= speed;
    }

    if (snowflake.yPos < skier.yPos) {
        skier.yPos -= speed;
    }

    if (snowflake.xPos > skier.xPos) {
        skier.xPos += speed;
    }

    if (snowflake.yPos > skier.yPos) {
        skier.yPos += speed;
    }

}


//function to restart the game if a collision has occured
function gameOver(snowflake) { //there's problems with this function
    //draw a new background that says you've lost and refresh the screen to restart
    // context.fillStyle="#FFFFFF";
    // context.fillRect(0, 0, canvasWidth, canvasHeight);

    //reset the snowflake position
    snowflake.xPos = canvasWidth / 2;
    snowflake.yPos = canvasHeight / 2;
}


//main drawing function that calls the window request animation
function mainDrawing() {
    window.requestAnimationFrame(animate);
}

//load the window
window.onload = mainDrawing;

//handle the mouse movement
document.onmousemove = handleMouseMove;