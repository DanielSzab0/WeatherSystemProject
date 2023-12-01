package weatherSystem.repository;

import weatherSystem.entity.Weather;

import java.io.IOException;

public interface WeatherRepository {
    void saveToDatabase(Weather weatherData);
   Weather collectDataFromWeatherStack(String location, String date) throws IOException;
    void displayWeatherData(Weather weatherData);
}
