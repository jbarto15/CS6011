//Josh Barton - Rainfall HW

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        //create a variable called rainData that takes the name of the file
        RainData rainData = new RainData("/Users/joshbarton/Desktop/MSD2023/CS6011/Week1/Day2/Rainfall/src/rainfall_data.txt");

        //create an array list of type RainData and use the dataList method to get an array list of all the rain data (month, year, rainfall)
        ArrayList<RainData> dataList = rainData.dataList();

        //variable to store the statistics for each month and the overall average rainfall for all months
        String textToAddToFile = new String();

        //variable to count the overall total average rainfall
        double sumOfAverageRainfall = 0.0;

        //use a for loop from 0 to 11 since there are 12 months in a year and display the average rainfall for that month
        for (int i = 0; i < 12; i++ ) {
            String targetMonth = dataList.get(i).getMonth();
            double averageRainfall = rainData.findAverageRainfall(dataList, targetMonth);
            System.out.println("The average rainfall amount for " + targetMonth + " is " + averageRainfall);
            sumOfAverageRainfall += averageRainfall;
            textToAddToFile += "The average rainfall amount for " + targetMonth + " is " + averageRainfall + '\n';
        }

        //get the overall average rainfall for all of the months
        double overallTotalRainfall = sumOfAverageRainfall / 12;
        System.out.println("The overall average rainfall amount is " + overallTotalRainfall + " inches.");
        textToAddToFile += "The overall average rainfall amount is " + overallTotalRainfall + " inches." + '\n';

        //call the outputStatistics method to add the statistics to the file
        rainData.outputStatistics("/Users/joshbarton/Desktop/MSD2023/CS6011/Week1/Day2/Rainfall/src/rainfall_results.txt", textToAddToFile);
    }
}