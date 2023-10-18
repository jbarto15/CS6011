"use strict";

//an array of ints
let integerArray = [2,4,6,7,9,1];
//an array of floats
let floatArray = [2.2, 4.5, 6.3, 7.7, 9.3, 1.5];
//an array of strings
let stringArray = ["hello", "my", "name", "is", "Josh", "Barton"];
//an array of a mix of these things
let mixArray = [2.4, 4, "hello", 7.7, 1, "josh"];

//function called selection sort that will take an array and sort it
function selectionSort(array, compareTo) {
    //define some variables that will help us sort
    let sortedArray = new Array();
    //use the findMinPos function and store that value as value 0 of the sorted array
    sortedArray[0] = findMinLocation(array);
    //loop through the array
    for (let i = 0; i < array.length; i++) {
        //check if the value we're on is greater than min
        if (array[i] > sortedArray[0]) {
            sortedArray.push(array[i]); //add array item to the back of the array
        } else if (array[i] < sortedArray[0]) {
            sortedArray.unshift(array[i]); //add array item to the front of the list using the unshift method
        }
    }
    //return the sorted array
    return sortedArray;
}

//call selection sort function
console.log("Sorted Integer Array: ");
console.log(selectionSort(integerArray));
console.log("Sorted Float Array: ");
console.log(selectionSort(floatArray));
console.log("Sorted String Array: ");
console.log(selectionSort(stringArray));


//helper function called findMinLocation()
function findMinLocation(array) {
    //define some variables that will help us find min location in array
    let minItem = array[0];
    let minPosition = 0;
    //loop over the array of integers
    for (let i = 1; i < array.length; i++) {
        if (array[i] < minItem) {
            minItem = array[i];
            minPosition = i;
        }
    }
    return minItem;
}

//call find min location function
console.log(findMinLocation(integerArray));

console.log(findMinLocation(floatArray));

console.log(findMinLocation(stringArray));

console.log(findMinLocation(mixArray));


//comparison function that returns whether or not a is less than be
function compareTo(a,b) {
    //compare a and b

    // If a is less than b, store the comparison 
    if (a < b) {
        const abComparison = a.localeCompare(b);
    }

    return abComparison;
}

//call the selectedSort function with the compareTo
console.log(selectionSort(integerArray, compareTo));



//create a person object with first and last name member variables
let person = [{firstName: "Josh", lastName: "Barton"}, {firstName: "Brittney", lastName: "Porter"}, {firstName: "Roonie", lastName: "Barton"}];

//print the person object
console.log(person);


