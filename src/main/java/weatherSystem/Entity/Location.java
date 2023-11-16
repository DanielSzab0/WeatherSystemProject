package weatherSystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "location")
public class Location extends BaseIdClass{
    private String cityName;
    private String region;
    private String countryName;
    private double lat;
    private double lon;

    public void validate() throws IllegalArgumentException {
        if (cityName == null || cityName.trim().isEmpty()) {
            throw new IllegalArgumentException("City name cannot be empty");
        }

        if (countryName == null || countryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Country name cannot be empty");
        }

        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("Invalid latitude value. Latitude must be between -90 and 90.");
        }

        if (lon < -180 || lon > 180) {
            throw new IllegalArgumentException("Invalid longitude value. Longitude must be between -180 and 180.");
        }
    }
}
