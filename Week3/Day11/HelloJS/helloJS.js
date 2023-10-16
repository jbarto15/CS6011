"use strict";

function mainFunction() {
    document.writeln("hello world");
}

function helloWorld() {
    console.log("hello world");

    //create a literal array variable
    let myArray = ["basketball", 25, true, 4.5];

    //create an object
    let myObject = {name: "Josh", age: "26"};

    //add the object to the array
    myArray.push(myObject);

    //print the array
    console.log(myArray);

    //modify the array somehow
    myArray.push("add this string to my array");

    //print array again
    console.log(myArray);
}

// window.onload = function() {
//     mainFunction();
//     helloWorld();
// };

//call hello world function
helloWorld();


//define a function that adds two parameters together
function myFirstFunction(a,b) {
    let addition = a + b;

    console.log("Result: ", addition);
}

//define a second function with different syntax
let mySecondFunction = function(a,b) {
    let addition = a + b;
    console.log("Result: ", addition);
}

//************************TEST FUNCTION WITH DIFFERENT TYPES****************************

//call the first function
myFirstFunction(2, 4);
myFirstFunction(2.5, 8.7);
myFirstFunction("hello", "world");
myFirstFunction("hello", 26);

//call the second function
mySecondFunction(2, 8);
mySecondFunction(4.2, 8.9);
mySecondFunction("Josh ", "Barton");
mySecondFunction("hello", 2.5);
mySecondFunction(2.87, 8);
