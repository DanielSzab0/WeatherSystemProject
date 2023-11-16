package weatherSystem.Service;

import org.hibernate.query.Query;
import weatherSystem.Entity.AvgWeather;
import weatherSystem.Entity.Weather;
import weatherSystem.Repository.AvgWeatherRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import weatherSystem.connection.Connection;

import java.util.Arrays;
import java.util.List;

public class AvgWeatherImplementation implements AvgWeatherRepository {
    private final Session session = Connection.sessionFactory.openSession();

    @Override
    public void calculatAarage(String cityName, String date) {
        try{
            Transaction transaction = session.beginTransaction();

            Query<Object[]> query = session.createQuery(
                    "SELECT AVG(w.humidity), AVG(w.pressure), AVG(w.temperature), AVG(w.windSpeed) " +
                            "FROM Weather w " +
                            "WHERE w.cityName = :cityName AND w.date = :date", Object[].class);
            query.setParameter("cityName", cityName);
            query.setParameter("date", date);

            List<Object[]> result = query.list();

            if (!result.isEmpty()) {
                Object[] averages = result.get(0);

                AvgWeather avgWeatherData = new AvgWeather();
                avgWeatherData.setCityName(cityName);
                avgWeatherData.setDate(date);
                avgWeatherData.setHumidity((Double) averages[0]);
                avgWeatherData.setPressure((Double) averages[1]);
                avgWeatherData.setTemperature((Double) averages[2]);
                avgWeatherData.setWindSpeed((Double) averages[3]);

            }
                result.forEach(avg -> System.out.println(Arrays.toString(avg)));

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAvgToDatabase(AvgWeather avgWeatherData) {
        try {
            Session session = Connection.sessionFactory.openSession();
            session.beginTransaction();
            session.persist(avgWeatherData);
            System.out.println("Weather data successfully saved to database.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Weather> getWeatherByNameAndDate(String cityName, String date) {
        try {
            return session.createQuery("SELECT w FROM Weather w WHERE w.cityName = :cityName AND w.date = :date", Weather.class)
                    .setParameter("cityName", cityName)
                    .setParameter("date", date)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
