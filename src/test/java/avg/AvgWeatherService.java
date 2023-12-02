package avg;

import org.junit.jupiter.api.*;

public class AvgWeatherService {

    AvgWeatherService avgWeatherService;

    @BeforeEach
    public void setUp() {
        avgWeatherService = new AvgWeatherService();
        System.out.println("Initializing the AvgWeatherService class.");
    }

    @AfterEach
    public void cleanUp() {
        avgWeatherService = null;
        System.out.println("Cleaning the AvgWeatherService class from memory.");
    }

    @BeforeAll
    public static void startingTestClass() {
        System.out.println("Starting the Average Weather Test class");
    }

    @AfterAll
    public static void endingTestClass() {
        System.out.println("Ending the Average Weather Test class");
    }

    @Test
    public void calculateAverageTest() {
        String cityName = "Bucuresti";
        String date = "2023-11-15";


    }
}
