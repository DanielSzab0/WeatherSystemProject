package weatherSystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather extends BaseIdClass{
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    private String cityName;
    private String date;
    private int temperature;
    private String windDirection;
    private int windSpeed;
    private int pressure;
    private int humidity;

//    public Weather(Location location,String cityName, String date, String windDirection, int windSpeed, int temperature, int pressure, int humidity) {
//        this.location = location;
//        this.cityName = cityName;
//        this.date = date;
//        this.windDirection = windDirection;
//        this.windSpeed = windSpeed;
//        this.temperature = temperature;
//        this.pressure = pressure;
//        this.humidity = humidity;
//    }
}
