package weatherSystem.Service;

import org.hibernate.Session;
import weatherSystem.connection.Connection;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;

public class ToFileService {
    private static Session session = Connection.sessionFactory.openSession();
//    LocationImplementation locationImplementation = new LocationImplementation();
    WeatherImplementation weatherImplementation = new WeatherImplementation();

    String fileName;
    public void createFile(String fileName) {
        try {
            this.fileName = fileName;
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeStatisticsToFile(String date) {
        try {
            String data = weatherImplementation.getStatisticsByPeriod(date).toString();
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(data);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void insertDataFromFile(String fileName) {
        FileInputStream fis = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            session.beginTransaction();
            File filename1 = new File(fileName);
            filename1.getPath();
            session.createQuery("");
        fis = new FileInputStream(filename1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

//    public void writeLocationToFile() {
//        try {
//            String data = locationImplementation.getLocations().toString();
//            FileWriter myWriter = new FileWriter("data_file.txt");
//            myWriter.write(data);
//            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }
}