package weatherSystem.service;

import org.hibernate.Session;
import org.hibernate.Transaction;

import weatherSystem.entity.Location;
import weatherSystem.entity.Weather;
import weatherSystem.repository.WeatherRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import weatherSystem.connection.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class WeatherService implements WeatherRepository {
    private final Session session = Connection.sessionFactory.openSession();
    private static final String WEATHERSTACK_API_KEY = "8ebca2f498ca14abd209c784636ad015";
    private static final String WEATHERSTACK_API_URL = "http://api.weatherstack.com/current";

    private static String buildApiUrlWeatherStack(String location) {
        return String.format("%s?access_key=%s&query=%s", WEATHERSTACK_API_URL, WEATHERSTACK_API_KEY, location);
    }

    @Override
    public void saveToDatabase(Weather weatherData) {
        try {
            Transaction transaction = session.beginTransaction();
            session.persist(weatherData);
            transaction.commit();
            System.out.println("Weather data successfully saved to database.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String sendHttpRequest(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();
        connection.disconnect();

        return response.toString();
    }

    private Weather parseResponse(String jsonResponse, String date, String cityName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        int temperature = rootNode.path("current").path("temperature").asInt();
        int pressure = rootNode.path("current").path("pressure").asInt();
        int humidity = rootNode.path("current").path("humidity").asInt();
        int windSpeed = rootNode.path("current").path("wind_speed").asInt();
        String windDirection = rootNode.path("current").path("wind_dir").asText();
        Weather weather = new Weather();
        weather.setDate(date);
        Location weatherLocation = getLocationByName(cityName);
        weather.setLocation(weatherLocation);
        weather.setCityName(cityName);
        return new Weather(weatherLocation, cityName, date, humidity, windSpeed, pressure, windDirection, temperature);
    }


    @Override
    public Weather collectDataFromWeatherStack(String location, String date) throws IOException {
        try {String apiUrl = buildApiUrlWeatherStack(location);
            String jsonResponse = sendHttpRequest(apiUrl);
            return parseResponse(jsonResponse, date, location);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void displayWeatherData(Weather weatherData) {
        if (weatherData != null) {
            System.out.println("Temperature: " + weatherData.getTemperature() + "°C");
            System.out.println("Pressure: " + weatherData.getPressure() + " hPa");
            System.out.println("Humidity: " + weatherData.getHumidity() + "%");
            System.out.println("Wind: " + weatherData.getWindSpeed() + " m/s " + weatherData.getWindDirection());
        } else {
            System.out.println("Failed to retrieve weather data");
        }
    }

    public Location getLocationByName(String cityName) {
        try {
            return session.createQuery("FROM Location WHERE cityName = :cityName", Location.class)
                    .setParameter("cityName", cityName)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Weather> getStatisticsByPeriod(String date) {
        try {
            List<Weather> weathers = session.createQuery("SELECT w FROM Weather w WHERE w.date = :date", Weather.class)
                    .setParameter("date", date)
                    .getResultList();
            weathers.forEach(w ->
                    System.out.println(w.getId() + ". " + w.getDate() + ", " + w.getCityName() + ", Temperature: " +
                            w.getTemperature() + ", Humidity: " + w.getHumidity() + ", Pressure: " + w.getPressure() +
                            ", Wind speed: " + w.getWindSpeed() + ", Wind direction: " + w.getWindDirection() + "."));
            return weathers;
        } catch (Exception e) {
            System.out.println("Invalid data input");
            e.printStackTrace();
            return null;
        }
    }
}
