package weatherSystem;

import weatherSystem.Entity.Location;
import weatherSystem.Entity.Weather;
import weatherSystem.Service.AvgWeatherImplementation;
import weatherSystem.Service.LocationImplementation;
import weatherSystem.Service.WeatherImplementation;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        LocationImplementation locationImplementation = new LocationImplementation();
        WeatherImplementation weatherImplementation = new WeatherImplementation();
        AvgWeatherImplementation avgWeather = new AvgWeatherImplementation();

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("\nPlease chose an option from fallowing:\n" +
                    "1. To add a new location.\n" +
                    "2. To edit location\n" +
                    "3. To display all locations added.\n" +
                    "4. To insert weather values.\n" +
                    "5. To retrieve average values.\n" +
                    "6. To retrieve weather statistics by period.\n" +
                    "7. To exit.");
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

