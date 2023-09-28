//Josh Barton - Rainfall Assignment

import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;

public class RainData {
    private String _month;
    private int _year;
    private Double _rainfall;
    private String _filePath;

    //a get month method that will be useful in determining the averages
    public String getMonth() {
        return _month;
    }

    //a get rainfall method that will help in determining averages
    public double getRainfall() {
        return _rainfall;
    }


    //constructor that takes a filePath and assigns the filePath given to our class variable _filePath
    public RainData(String filePath) throws FileNotFoundException {
        _month = "";
        _year = 0;
        _rainfall = 0.0;
        _filePath = filePath;
    }

    //constructor that takes in a month, year, and average rainfall
    public RainData(String month, Integer year, Double rainFall) throws FileNotFoundException {
        _month = month;
        _year = year;
        _rainfall = rainFall;
    }

    //method that inputs the file and puts it into an array list of type RainData
    public ArrayList<RainData> dataList() throws FileNotFoundException {
        //read in the file
        FileInputStream fileInputStream = new FileInputStream(_filePath);

        //scanner that will read in the data from the input stream
        Scanner scanner = new Scanner(fileInputStream);

        //create an Array List that stores the lines of data
        ArrayList<RainData> dataList = new ArrayList<RainData>();

        //read in the file as long as there is a line to read
        while (scanner.hasNextLine()) {
            //split up the line using \\s+ which will split the line when it encounters a space
            String[] line = (scanner.nextLine()).split("\\s+");

            //check to make sure the line we put into the string array has all the components, i.e. is greater than 1 since the line has should have 0,1,and 2 components
            if (line.length > 1) {
                String month = line[0];
                Integer year = Integer.parseInt(line[1]);
                Double rainfall = Double.parseDouble(line[2]);

                //create a new object with the data received from scanning in the file
                RainData newData = new RainData(month, year, rainfall);

                //add the new objects to the array list of type RainData
                dataList.add(newData);
            }
        }
        return dataList;
    }


    //method that finds the average rainfall for a particular month
    public double findAverageRainfall(ArrayList<RainData> dataList, String month) {
        double totalRainfall = 0.0;
        double numOfMonths = 0;

        //loop through the dataList which is an array list of type RainData
        for (RainData data : dataList) {
            //check to see if the month in each data member of the arrayList is equal to the month passed into the method. If so, updata total rainfall by adding the rainfall of that month
            if (data.getMonth().equals(month)) {
                totalRainfall += data.getRainfall();
                //update the number of months so that we will have a number to divide by
                numOfMonths++;
            }
        }
        //return the average for the specific month given
        return totalRainfall / numOfMonths;
    }

    //method that will add the averages for each month and the overall average to the text file
    public void outputStatistics(String filePath, String statisticsToAdd) throws IOException {
        FileWriter myWriter = new FileWriter( filePath );
        myWriter.write( statisticsToAdd );
        myWriter.close();
    }

}



