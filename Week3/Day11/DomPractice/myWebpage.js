
"use strict";
function mainFunction() {
//set the style for the background color
document.body.style.backgroundColor = 'rgb(226, 200, 166)';

//create the header at the top of the webpage
let header1 = document.createElement('h1');
header1.textContent = "Josh Barton";
header1.style.color = 'blue';
header1.style.textAlign = 'center';

//create the second header of the webpage
let header2 = document.createElement('h2');
header2.textContent = "Welcome to my webpage!";
header2.style.color = 'blue';


//create paragraph element
let paragraph = document.createElement('p');
paragraph.textContent = "I am part of the MSD 2023 cohort and am excited to start building webpages."
paragraph.style.color = 'blue';

//create third header element
let header3 = document.createElement('h3');
header3.textContent = "Some of my favorite things to do:"
header3.style.color = 'blue';


//create unordered list
let unorderedList = document.createElement('ul');

//create a list item
let list1 = document.createElement('li');
list1.textContent = "Waterskiing and snowskiing.";
list1.style.color = 'blue';
//add item to unordered list
unorderedList.appendChild(list1);

let list2 = document.createElement('li');
list2.textContent = "Playing basketball, football, and soccer.";
list2.style.color = 'blue';

//add item to unordered list
unorderedList.appendChild(list2);

let list3 = document.createElement('li');
list3.textContent = "Mountain Biking.";
list3.style.color = 'blue';
//add item to unordered list
unorderedList.appendChild(list3);

let list4 = document.createElement('li');
list4.textContent = "Watching sports.";
list4.style.color = 'blue';

//add item to unordered list
unorderedList.appendChild(list4);

//create a break element
let pageBreak = document.createElement('br');

//create a division element
let division = document.createElement('div');

//create an image element
let image1 = document.createElement('img');
image1.setAttribute('src', 'mountainbiking.jpg');
image1.style.width = '350px';
image1.style.height = '250px';

//create second image elelment
let image2 = document.createElement('img');
image2.setAttribute('src', 'skier.webp');
image2.style.width = '350px';
image2.style.height = '250px';

//add a break element in the division between the pictures
let divisionBreak = document.createElement('br');
let divisionBreak1 = document.createElement('br');

//add the children to the parent division
division.appendChild(image2);
division.appendChild(divisionBreak);
division.appendChild(divisionBreak1);
division.appendChild(image1);


//append elements to the document body
document.body.appendChild(header1);
document.body.appendChild(header2);
document.body.appendChild(paragraph);
document.body.appendChild(header3);
document.body.appendChild(unorderedList);
document.body.appendChild(pageBreak);
document.body.appendChild(division);

}

mainFunction();
