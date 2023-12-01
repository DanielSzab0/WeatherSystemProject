package weatherSystem.connection;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import weatherSystem.entity.AvgWeather;
import weatherSystem.entity.Location;
import weatherSystem.entity.Weather;


public class Connection {
    private static Connection instance = new Connection();
    private Connection() {}

    public synchronized static Connection getInstance() {
        return instance;
    }

    public final static SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Location.class)
            .addAnnotatedClass(Weather.class)
            .addAnnotatedClass(AvgWeather.class)
            .buildSessionFactory();
}
