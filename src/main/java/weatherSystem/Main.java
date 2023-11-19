package weatherSystem;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import weatherSystem.Entity.Location;
import weatherSystem.Entity.Weather;
import weatherSystem.Service.AvgWeatherImplementation;
import weatherSystem.Service.LocationImplementation;
import weatherSystem.Service.ToFileService;
import weatherSystem.Service.WeatherImplementation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public void readCSVFile(String fileName) {
        try {
            CSVParser csvParser = CSVFormat.DEFAULT.parse(Files.newBufferedReader(Paths.get(fileName)));

            for (CSVRecord csvRecord : csvParser) {
                String cityName = csvRecord.get(0);
                String date = csvRecord.get(1);

                System.out.println("City: " + cityName + ", Date: " + date);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

//        Main yourClass = new Main();
//        yourClass.readCSVFile("file_data.csv");

        LocationImplementation locationImplementation = new LocationImplementation();
        WeatherImplementation weatherImplementation = new WeatherImplementation();
        AvgWeatherImplementation avgWeather = new AvgWeatherImplementation();
        ToFileService toFileService = new ToFileService();

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("\nPlease chose an option from fallowing:\n" +
                    "1. To add a new location.\n" +
                    "2. To edit location\n" +
                    "3. To display all locations added.\n" +
                    "4. To insert weather values.\n" +
                    "5. To retrieve average values.\n" +
                    "6. To retrieve weather statistics by period.\n" +
                    "7. To create a file or save data into a file.\n" +
                    "8. To restore data from file to database\n" +
                    "9. To exit.");
            int optNo = scanner.nextInt();
            scanner.nextLine();

            switch (optNo) {
                case 1:
                    System.out.println("Enter city name:");
                    String cityName = scanner.nextLine();

                    System.out.println("Enter region (optional):");
                    String region = scanner.nextLine();

                    System.out.println("Enter country name:");
                    String countryName = scanner.nextLine();

                    System.out.println("Enter latitude:");
                    double latitude = scanner.nextDouble();

                    System.out.println("Enter longitude:");
                    double longitude = scanner.nextDouble();

                    Location location = new Location(cityName, region, countryName, latitude, longitude);
                    locationImplementation.addLocation(location);
                    System.out.println("Location added successfully.");
                    break;

                case 2:
                    System.out.println("Enter location to edit.");
                    String location1 = scanner.nextLine();
                    System.out.println("Enter location's new name.");
                    String newLocation = scanner.nextLine();

                    locationImplementation.editLocation(location1, newLocation);
                    break;

                case 3:
                    locationImplementation.getLocations();
                    break;

                case 4:
                    System.out.println("Enter date (YYYY-MM-DD):");
                    String date = scanner.nextLine();

                    System.out.println("Enter location:");
                    cityName = scanner.nextLine();

                    System.out.println("Please chose:\n" +
                            "1. To download the weather data from internet.\n" +
                            "2. To insert manually the weather data from keyboard.");

                    int opt = scanner.nextInt();
                    if (opt == 1 || opt == 2) {
                        switch (opt) {
                            case 1:
                                try {
                                    Weather stackWeatherData = weatherImplementation.collectDataFromWeatherStack(cityName, date);
                                    weatherImplementation.displayWeatherData(stackWeatherData);
                                    weatherImplementation.saveToDatabase(stackWeatherData);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 2:
                                Weather weather = new Weather();
                                System.out.println("Enter temperature:");
                                weather.setTemperature(scanner.nextInt());
                                System.out.println("Enter pressure (usual range ~ 1000 hPa):");
                                weather.setPressure(scanner.nextInt());
                                System.out.println("Enter humidity (range 1-100%):");
                                weather.setHumidity(scanner.nextInt());
                                System.out.println("Enter wind speed:");
                                weather.setWindSpeed(scanner.nextInt());
                                System.out.println("Enter wind direction:");
                                weather.setWindDirection(scanner.next());
                                weather.setCityName(cityName);
                                weather.setDate(date);
                                weather.setLocation(weatherImplementation.getLocationByName(cityName));
                                weatherImplementation.saveToDatabase(weather);
                        }
                    } else {
                        System.out.println("Invalid option. You have to chose 1 or 2.");
                    }
                    break;

                case 5:
                    System.out.println("Enter the location name:");
                    cityName = scanner.nextLine();
                    System.out.println("Enter the date:");
                    date = scanner.nextLine();
                    avgWeather.calculatAarage(cityName, date);
                    break;

                case 6:
                    System.out.println("Enter the date for statistics:");
                    date = scanner.nextLine();
                    weatherImplementation.getStatisticsByPeriod(date);
                    break;

                case 7:
                    System.out.println("Please chose:\n" +
                            "1. To create a new file.\n" +
                            "2. To save data to a file.");

                    opt = scanner.nextInt();
                    scanner.nextLine();

                    if (opt == 1 || opt == 2) {
                        switch (opt) {
                            case 1:
                                System.out.println("Enter the file name.");
                                String fileName = scanner.nextLine().trim();

                                toFileService.createFile(fileName);
                                break;

                            case 2:
                                System.out.println("Enter the file name to save data into it.");
                                fileName = scanner.nextLine();
                                System.out.println("Enter the date for statistics to save into the file");
                                date = scanner.nextLine();
                                System.out.println("Enter the city name for statistics to save into the file");
                                cityName = scanner.nextLine();
                                toFileService.writeDataToFile(fileName, date, cityName);
                                break;
                        }
                    } else {
                        System.out.println("Invalid option. You have to chose 1 or 2.");
                    }
                    break;

                case 8:
                    System.out.println("Enter tha file name to restore data from:");
                    String fileName = scanner.nextLine();
                    System.out.println("Enter the date for which to restore data:");
                    date = scanner.nextLine();
                    System.out.println("Enter a city name corresponding to date:");
                    cityName = scanner.nextLine();
                    toFileService.restoreWeatherFromFileToDatabase(fileName, date, cityName);
                    break;

                case 9:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

