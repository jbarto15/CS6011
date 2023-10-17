//create a 10x10 times table
function createTimesTable () {
    //for loop that will iterate from 0-10
    for (let i = 0; i <= 10; i++) {
        //write to the document a table row for each iteration of the first for loop
        document.write("<tr>");

        //second for loop that will place the data in each row
        for (let j = 0; j <= 10; j++) {
            //create condition that will display numbers 1 - 10 in the first row as well as numbers 1 - 10 in the following rows
            if (i === 0) {
                document.write("<td>" + j + "</td>");
            } else if (j === 0) {
                document.write("<td>" + i + "</td>");
            } else {
                //for all other cells, display the multiplication result of i * j
                let data = i * j;
                document.write("<td>" + data + "</td>");
            }
        }
        //closing tag for the table row
        document.write("</tr>");
    }
}

//call the create times table function
createTimesTable();


//function to handle changing the background when the mouse is hovering over a particular cell
function changeCellColor() {
    //create a variable that will select and store all of the table data from the times table
    let tableCells = document.querySelectorAll("td");
    //use the for each method on the table cells variable to iterate over the cells
    tableCells.forEach(function(cell) {
        //add an event listener to each cell that responds to a mouseover
        cell.addEventListener("mouseover", function() {
            //change the background color when the mouse is hovering over the cell
            cell.style.backgroundColor = "lightblue";
        });
        //add an event listener to each cell that responds to a mouseout which is just when the mouse leaves the cell
        cell.addEventListener("mouseout", function() {
            //reset the background color when the mouse is not hovering over the cell
            cell.style.backgroundColor = "";
        });
    });
}

//call the change cell color function
changeCellColor();


//function to change the cell color on a click
function changeCellColorOnClick() {
    //variable to keep track of the selected cell
    let selectedCell = null;

    //create a variable that will select and store all of the table data from the times table
    let tableCells = document.querySelectorAll("td");
    //use the for each method on the table cells variable to iterate over the cells
    tableCells.forEach(function(cell) {
        //add an event listener to each cell that responds to a click
        cell.addEventListener("click", function() {
            if (selectedCell) {
                //reset the previously selected cell to its original state by removing the CSS style applied to it when it was clicked
                selectedCell.classList.remove("selected");
            }
            //check if the currently selected cell is different from the previously selected cell, if not, highlight the current cell
            if (selectedCell !== cell) {
                //highlight the current cell
                cell.classList.add("selected");
                selectedCell = cell;
            } else {
                //the selected cell is reset to null which deselects the cell
                selectedCell = null;
            }
        });
    });
}

//call the change cell color on click function
changeCellColorOnClick();



//function that will slowly change the background color when the user clicks the toggle background button
function toggleBackground() {
    
}
