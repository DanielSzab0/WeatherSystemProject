package weatherSystem.repository;

public interface AvgWeatherRepository{
    void calculateAverage(String cityName, String date);
//    void saveAvgToDatabase(AvgWeather avgWeatherData);
}
