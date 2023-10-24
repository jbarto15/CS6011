
"use strict";

//an array of ints
let integerArray = [2,4,6,7,9,1];
//second integer array
let integerArray2 = [2,4,3,6,5,7,9,1];
//an array of floats
let floatArray = [2.2, 4.5, 6.3, 7.7, 9.3, 1.5];
//an array of strings
let stringArray = ["hello", "my", "name", "is", "Josh", "Barton"];
//an array of a mix of these things
let mixArray = [2.4, 4, "hello", 7.7, 1, "josh"];

//create a person object with first and last name member variables
let person1 = {firstName: "Josh", lastName: "Barton"};
let person2 = {firstName: "Brittney", lastName: "Porter"};
let person3 = {firstName: "Roonie", lastName: "Barton"};
let personArray = [{firstName: "Josh", lastName: "Barton"}, {firstName: "Brittney", lastName: "Porter"}, {firstName: "Roonie", lastName: "Barton"}];
let personArray2 = [{firstName: "Deron", lastName: "Williams"}, {firstName: "Carlos", lastName: "Boozer"}, {firstName: "Tim", lastName: "Duncan"}]

//print the person object
//console.log(person);

//function called selection sort that will take an array and sort it
function selectionSort(array, compareTo) {
    //loop to go through the array
    for (let i = 0; i < array.length; i++) {
        //store the position of the minimum value in the array
        let minPosition = findMinLocation(array, i, compareTo);
        //store the value of the current element in the temporary variable
        const temp = array[i];
        //assign current position value to the value at the min location
        array[i] = array[minPosition];
        array[minPosition] = temp;
    }
    //print out the array
    console.log(array);
}


//helper function called findMinLocation()
function findMinLocation(array, index, compareTo) {
    //define some variables that will help us find min location in array
    let minPosition = index;
    //loop over the array of integers
    for (let i = index + 1; i < array.length; i++) {
       //check if the current element is less than the element at the min position using compareTo function
            if (compareTo(array[i], array[minPosition])) {
                minPosition = i;
            }
    }
    return minPosition;
}


//function to compare two items in the array
function compareTo(a, b) {
    //check if a and b are of the same type
    if (typeof a === 'string' && typeof b === 'string') {
        return a < b;
    }
    //check if a and b are of the same type
    if (typeof a === typeof b) {
        return a < b;
        
    } 
    return 0;
}


//function to compare two people objects by last name
function comparePeopleByFirstName(a, b) {
    //check if a and b are of the same type
    if (typeof a === typeof b) {
        if( a.firstName > b.firstName){
            return a > b;
        }
        if (a.firstName < b.firstName) {
            return -1;
        }
    }
    return 0;
}


//function to compare two people objects by first name
function comparePeopleByLastName(a, b) {
    if (typeof a === typeof b) {
        return a.lastName < b.lastName; 
    }

    if (a.lastName > b.lastName) {
        return -1;
    }
    return 0;
}

//call my selection sort function
selectionSort(integerArray, compareTo);
selectionSort(floatArray, compareTo);
selectionSort(stringArray,compareTo);
selectionSort(mixArray, compareTo);
console.log("Integer Array 2: ");
selectionSort(integerArray2, compareTo);


let stringz = ["deron", "williams", "andre", "kirilenko", "carlos", "boozer"];

selectionSort(stringz, compareTo);

selectionSort(personArray, comparePeopleByFirstName);

selectionSort(personArray2, comparePeopleByLastName);

