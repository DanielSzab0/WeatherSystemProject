package weatherSystem.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.Session;
import weatherSystem.Entity.Location;
import weatherSystem.Repository.LocationRepository;
import weatherSystem.connection.Connection;

import java.util.List;

public class LocationImplementation implements LocationRepository {
    Connection connection = Connection.getInstance();

    @Override
    public void addLocation(Location location) {
        try {
            EntityManager entityManager = connection.sessionFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(location);
            location.validate();
            entityManager.getTransaction().commit();
            System.out.println("Location successfully added.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Location> getLocations() {
        EntityManager entityManager  = connection.sessionFactory.createEntityManager();
        List<Location> locations = entityManager.createQuery("FROM Location", Location.class).getResultList();
        locations.forEach(loc-> System.out.println(loc.getId()+ ". " +loc.getCityName()+ ", " +loc.getCountryName() + "."));
        return locations;
    }

    public void editLocation(String cityName, String newCityName) {
        try (Session session = connection.sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("UPDATE Location l SET l.cityName = :newCityName " + "WHERE l.cityName = :cityName")
                    .setParameter("cityName", cityName)
                    .setParameter("newCityName", newCityName);
                    int v = query.executeUpdate();
            if (v != 0) {
                System.out.printf("Location %s successfully edited", cityName);
            } else {
                System.out.println("Location not found");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
