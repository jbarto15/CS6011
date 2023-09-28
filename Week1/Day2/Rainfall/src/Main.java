//Josh Barton - Rainfall HW

import java.io.IOException;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) throws IOException {

        //create a variable called rainfallData that takes the name of the file
        RainData rainfallData = new RainData("/Users/joshbarton/Desktop/MSD2023/CS6011/Week1/Day2/Rainfall/src/rainfall_data.txt");

        //create an array list of type RainData and use the dataList method to get an ArrayList of all the rain data (month, year, rainfall)
        ArrayList<RainData> dataList = rainfallData.dataList();

        //variable to store the statistics for each month and the overall average rainfall for all months
        String textToAddToFile = new String();

        //variable to count the overall total average rainfall
        double sumOfAverageRainfall = 0.0;

        //use a for loop from 0 to 11 since there are 12 months in a year and display the average rainfall for that month
        for (int i = 0; i < 12; i++ ) {
            String targetMonth = dataList.get(i).getMonth();
            double averageRainfall = rainfallData.findAverageRainfall(dataList, targetMonth);

            //variable to change the averageRainfall into the proper format of two places after decimal
            DecimalFormat avRain = new DecimalFormat("0.00");
            String avRainProperFormat = avRain.format(averageRainfall);

            //print out the average rainfall for a particular month
            System.out.println("The average rainfall amount for " + targetMonth + " is " + avRainProperFormat);

            //take the average rainfall for each month and add it to the sum of all the averages variable
            sumOfAverageRainfall += averageRainfall;

            //add a string for the current month that will eventually display rain averages in the rain_results.txt file
            textToAddToFile += "The average rainfall amount for " + targetMonth + " is " + avRainProperFormat + '\n';
        }

        //get the overall average rainfall for all of the months
        double overallAverageTotalRainfall = sumOfAverageRainfall / 12;

        //variable to change the overallAverageTotalRainfall into the proper format of two places after decimal
        DecimalFormat overallTotRain = new DecimalFormat("0.00");
        String overallRainProperFormat = overallTotRain.format(overallAverageTotalRainfall);

        //print out the overall average total rainfall
        System.out.println("The overall average rainfall amount is " + overallRainProperFormat + " inches.");

        //add text to the rainfall_results.txt file
        textToAddToFile += "The overall average rainfall amount is " + overallRainProperFormat + " inches." + '\n';

        //call the outputStatistics method to add the statistics to the file
        rainfallData.outputStatistics("/Users/joshbarton/Desktop/MSD2023/CS6011/Week1/Day2/Rainfall/src/rainfall_results.txt", textToAddToFile);
    }
}