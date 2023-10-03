import java.io.*;

class HTTPResponse {
    //string to store the filename
    String filename;
    //file to store the file
    File htmlFile;

    //input stream to input the file
    FileInputStream inputFile;

    //input stream for the error file
    FileInputStream inputErrorFile;

    //an output stream variable
    OutputStream outputStream;

    String errorFilePath;


    //constructor
    public HTTPResponse(String filename, OutputStream outputStream) throws IOException {

        this.outputStream = outputStream;
        this.filename = filename;
        this.htmlFile = new File("/Users/joshbarton/Desktop/MSD2023/CS6011/Week1/Day4/MyHttpServer/resources/" + filename);
        this.inputFile = new FileInputStream(this.htmlFile);
        this.errorFilePath = "/Users/joshbarton/Desktop/MSD2023/CS6011/Week1/Day4/MyHttpServer/resources/errorHtml.html";
        this.inputErrorFile = new FileInputStream(errorFilePath);

        //create an exception if the htmlFile does not exist
        if (!htmlFile.exists()) {
        IOException e = new IOException("File requested does not exist!");
        throw e;
        }

    }

    //method

    //stream in the file if the file exists and then transfer it to the output stream to the client
    public void streamInFile() throws IOException {
        //create a File for the error file
        File errorFile = new File(errorFilePath);
        //if filename == / or /index.html then stream in the file
        if (htmlFile.exists()) {
            //create a filestream variable that streams in the html file
            FileInputStream inputFile = new FileInputStream(htmlFile);
        }
    }

    public void streamOutFile() throws IOException {
        if (htmlFile.exists()) {
            //give the header information to the client
            outputStream.write("HTTP/1.1 200 OK\n".getBytes());
            //variable that will store a split of the filename so that just the type of file is given
            String[] parts = filename.split("\\.");
            String filetype = parts[parts.length - 1];
            outputStream.write(("Content-Type: text/" + filetype + "\n").getBytes());
            outputStream.write("\n".getBytes());
            //using the transferTo method in the input FileInputStream class, transfer the requested file directly to the client output stream
            inputFile.transferTo(outputStream);

            //flush the output stream
            outputStream.flush();

            //close the output stream
            outputStream.close();
        } else {
            //give the header information to the client and the file
            outputStream.write("HTTP/1.1 404 Not Found\n".getBytes());
            outputStream.write("Content-Type: text/html\n".getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write("404 Not Found\n".getBytes());
            inputErrorFile.transferTo(outputStream);

            //flush the output stream
            outputStream.flush();

            //close the output stream
            outputStream.close();
        }
    }
}

