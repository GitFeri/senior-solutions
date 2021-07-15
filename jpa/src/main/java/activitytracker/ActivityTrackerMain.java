package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class ActivityTrackerMain {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        EntityManager manager = factory.createEntityManager();


        manager.getTransaction().begin();
        manager.persist(new Activity(LocalDateTime.of(2021,6,7,10,10),"First activity",ActivityType.BIKING));
        manager.getTransaction().commit();

        manager.getTransaction().begin();
        manager.persist(new Activity(LocalDateTime.of(2021,8,9,11,12),"Second activity",ActivityType.BASKETBALL));
        manager.getTransaction().commit();

        manager.close();
        factory.close();
    }
}
