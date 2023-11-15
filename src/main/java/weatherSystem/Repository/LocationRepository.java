package weatherSystem.Repository;

import weatherSystem.Entity.Location;

import java.util.List;

public interface LocationRepository {
     void addLocation(Location location);
     List<Location> getLocations();

}
