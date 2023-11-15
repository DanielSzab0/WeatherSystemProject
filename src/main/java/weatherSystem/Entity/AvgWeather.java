package weatherSystem.Entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvgWeather extends BaseIdClass{
//    @OneToOne(mappedBy = "id")
//    private Location locationId;
    private String cityName;
    private String date;
    private String windDirection;
    private double windSpeed;
    private double temperature;
    private double pressure;
    private double humidity;
}
