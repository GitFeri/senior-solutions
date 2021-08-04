package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class ActivityTrackerMain {
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {

        ActivityTrackerMain main = new ActivityTrackerMain();
        main.openPersistent();

        main.createActivityToPersistent(new Activity(
                LocalDateTime.of(2000, 6, 7, 10, 10),
                "First activity", ActivityType.BIKING));

        main.createActivityToPersistent(new Activity(
                LocalDateTime.of(2000, 8, 9, 11, 12),
                "Second activity", ActivityType.BASKETBALL));

        Activity activity = new Activity(
                LocalDateTime.of(2020, 8, 9, 11, 12),
                "Second activity", ActivityType.RUNNING);
        activity.setLabels(List.of("első jel","második jel"));
        main.createActivityToPersistent(activity);



        main.closePersistent();
    }

    private void openPersistent() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        entityManager = entityManagerFactory.createEntityManager();
    }

    private void createActivityToPersistent(Activity activity) {
        entityManager.getTransaction().begin();
        entityManager.persist(activity);
        entityManager.getTransaction().commit();
    }

    private void closePersistent() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
