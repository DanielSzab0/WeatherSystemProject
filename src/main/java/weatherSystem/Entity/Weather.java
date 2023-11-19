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
    private int humidity;
    private int pressure;
    private int temperature;
    private String windDirection;
    private int windSpeed;

}
