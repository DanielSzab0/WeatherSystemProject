package location;

import org.junit.jupiter.api.*;
import weatherSystem.entity.Location;
import weatherSystem.service.LocationService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationServiceTest {

    LocationService locationService;

    @BeforeEach
    public void setUp() {
        locationService = new LocationService();
        System.out.println("Initializing the LocationService class.");
    }

    @AfterEach
    public void cleanUp() {
        locationService = null;
        System.out.println("Cleaning the LocationService class from memory.");
    }

    @BeforeAll
    public static void startingTestClass() {
        System.out.println("Starting the Location Test class.");
    }

    @AfterAll
    public static void endingTestClass() {
        System.out.println("Ending the Location Test class.");
    }

    @Test
    public void getLocationsTest() {
        System.out.println("Running the test for get locations");

        List<String> expectedLocations = Arrays.asList(
                "1. Timisoara, Romania.",
                "2. Bucuresti, Romania.",
                "3. Constanta, Romania."
        );

        List<Location> result = locationService.getLocations();

        List<String> actualLocations = result.stream()
                .map(location -> location.getId() + ". " + location.getCityName() + ", " + location.getCountryName() + ".")
                .toList();

        assertEquals(expectedLocations, actualLocations);
    }
}
