package weatherSystem.connection;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import weatherSystem.Entity.AvgWeather;
import weatherSystem.Entity.Location;
import weatherSystem.Entity.Weather;


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
