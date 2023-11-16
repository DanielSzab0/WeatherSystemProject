package weatherSystem.Repository;

import weatherSystem.Entity.AvgWeather;

public interface AvgWeatherRepository{
    void calculatAarage(String cityName, String date);
//    void saveAvgToDatabase(AvgWeather avgWeatherData);
}
