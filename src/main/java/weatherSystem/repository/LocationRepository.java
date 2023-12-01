package weatherSystem.repository;

import weatherSystem.entity.Location;

import java.util.List;

public interface LocationRepository {
     void addLocation(Location location);
     List<Location> getLocations();

}
