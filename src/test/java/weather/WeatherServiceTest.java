package weather;

import org.junit.jupiter.api.*;
import weatherSystem.entity.Weather;
import weatherSystem.service.WeatherService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherServiceTest {

    WeatherService weatherService;

    @BeforeEach
    public void setUp() {
        weatherService = new WeatherService();
        System.out.println("Initializing the WeatherService class.");
    }

    @AfterEach
    public void cleanUp() {
        weatherService = null;
        System.out.println("Cleaning the WeatherService class from memory.");
    }

    @BeforeAll
    public static void startingTestClass() {
        System.out.println("Starting the Weather Test class.");
    }

    @AfterAll
    public static void endingTestClass() {
        System.out.println("Ending the Weather Test class.");
    }

    @Test
    public void statisticsByPeriodTest() {
        String date = "2023-11-16";
        System.out.println("Running the test for statistics by period.");

        List<Weather> result = weatherService.getStatisticsByPeriod(date);

        result.forEach(weather -> {
                assertEquals("2023-11-16", weather.getDate());
                assertEquals("Constanta", weather.getCityName());
                assertEquals(7, weather.getTemperature());
                assertEquals(62, weather.getHumidity());
                assertEquals(1018, weather.getPressure());
                assertEquals(9, weather.getWindSpeed());
                assertEquals("WSW", weather.getWindDirection());
        });
    }
}