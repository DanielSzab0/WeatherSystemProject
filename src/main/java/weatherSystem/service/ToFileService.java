package weatherSystem.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.hibernate.Session;


import weatherSystem.entity.Location;
import weatherSystem.entity.Weather;
import weatherSystem.connection.Connection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ToFileService {
    private final static Session session = Connection.sessionFactory.openSession();
//    LocationService locationImplementation = new LocationService();

    private WeatherService weatherImplementation = new WeatherService();

    public void createFile(String fileName) {
        try {
            File file = new File(fileName);

                if (file.createNewFile()) {
                    System.out.println("File created: " + fileName);
                } else {
                    System.out.println("File already exists.");
                }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeWeatherDataToFile(String fileName, String date, String cityName) {
        try(CSVWriter csvWriter = new CSVWriter(new FileWriter(fileName))) {
            session.beginTransaction();

            List<String[]> selectedData =new ArrayList<>();

            List<Object[]> weathers = session.createQuery(
                            "SELECT w.cityName, w.date, w.humidity, w.pressure, w.temperature, w.windDirection, w.windSpeed " +
                                    "FROM Weather w " +
                                    "WHERE w.date = :date AND w.cityName = :cityName", Object[].class)
                    .setParameter("date", date)
                    .setParameter("cityName", cityName)
                    .getResultList();

            for (Object[] row : weathers) {
                String[] rows = {
                        String.valueOf(row[0]),
                        String.valueOf(row[1]),
                        String.valueOf(row[2]),
                        String.valueOf(row[3]),
                        String.valueOf(row[4]),
                        String.valueOf(row[5]),
                        String.valueOf(row[6]),
                };
                selectedData.add(rows);
            }
            csvWriter.writeAll(selectedData);
            System.out.println("Successfully wrote to the file.");

            session.getTransaction().commit();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

//    private int findIndex(String[] headers, String columnName) {
//        for (int i = 0; i < headers.length; i++) {
//            if (headers[i].equalsIgnoreCase(columnName)) {
//                return i;
//            }
//        }
//        throw new IllegalArgumentException("Columns not found: " + columnName);
//    }

    public void restoreWeatherFromFileToDatabase(String fileName, String desiredDate, String desiredCityName) {
        Location wetherLocation = weatherImplementation.getLocationByName(desiredCityName);

        try {CSVReader csvReader = new CSVReader(new FileReader(fileName));
            session.beginTransaction();

//            String[] headers = csvReader.readNext();
            String[] column;
            while ((column = csvReader.readNext()) != null) {
//                int cityNameIndex = findIndex(headers, "cityName");
//                int dateIndex = findIndex(headers, "date");
//                int humidityIndex = findIndex(headers, "humidity");
//                int pressureIndex = findIndex(headers, "pressure");
//                int temperatureIndex = findIndex(headers, "temperature");
//                int windDirectionIndex = findIndex(headers, "windDirection");
//                int windSpeedIndex = findIndex(headers, "windSpeed");

                String cityName = column[0];
                String date = column[1];

                if (date.equalsIgnoreCase(desiredDate) && cityName.equalsIgnoreCase(desiredCityName)) {
                    int humidity = Integer.parseInt(column[2]);
                    int pressure = Integer.parseInt(column[3]);
                    int temperature = Integer.parseInt(column[4]);
                    String windDirection = column[5];
                    int windSpeed = Integer.parseInt(column[6]);

                    Weather weather = new Weather();
                    weather.setLocation(wetherLocation);
                    weather.setCityName(cityName);
                    weather.setDate(date);
                    weather.setHumidity(humidity);
                    weather.setPressure(pressure);
                    weather.setTemperature(temperature);
                    weather.setWindDirection(windDirection);
                    weather.setWindSpeed(windSpeed);

                    weatherImplementation.saveToDatabase(weather);
                }
            }

            session.getTransaction().commit();

            System.out.println("Data successfully restored from file to database.");
        } catch(IOException | CsvValidationException e) {
            System.out.println("An error occurred while reading the Excel file.");
            e.printStackTrace();
        }
    }
}