package weatherSystem.Repository;

import weatherSystem.Entity.Weather;

import java.io.IOException;

public interface WeatherRepository {
    void saveToDatabase(Weather weatherData);
   Weather collectDataFromWeatherStack(String location, String date) throws IOException;
    void displayWeatherData(Weather weatherData);
}
