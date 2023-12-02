package weatherSystem.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.Session;
import weatherSystem.entity.Location;
import weatherSystem.repository.LocationRepository;
import weatherSystem.connection.Connection;

import java.util.List;

public class LocationService implements LocationRepository {
    private final EntityManager entityManager = Connection.sessionFactory.createEntityManager();
    private final Session session = Connection.sessionFactory.openSession();

    @Override
    public void addLocation(Location location) {
        try {
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
        List<Location> locations = entityManager.createQuery("FROM Location ORDER BY id", Location.class).getResultList();
        locations.forEach(loc -> System.out.println(loc.getId()+ ". " +loc.getCityName()+ ", " +loc.getCountryName() + "."));
        return locations;
    }

    public void editLocation(String cityName, String newCityName) {
        try {
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
