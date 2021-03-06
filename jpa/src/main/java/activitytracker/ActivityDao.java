package activitytracker;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Data
@AllArgsConstructor
public class ActivityDao {

    private EntityManagerFactory entityManagerFactory;

    public void saveActivity(Activity activity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(activity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Activity findActivityById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Activity activity = entityManager.find(Activity.class, id);
        entityManager.close();
        return activity;
    }

    public List<Activity> listActivities() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Activity> activities = entityManager
                .createQuery("SELECT e from Activity e order by e.description", Activity.class)
                .getResultList();
        entityManager.close();
        return activities;
    }

    public void updateActivity(long id, String description) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Activity activity = entityManager.find(Activity.class, id);
        entityManager.getTransaction().begin();
        activity.setDescription(description);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Activity findActivityByIdWithLabels(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Activity activity = entityManager
                .createQuery("select distinct a from Activity a left join fetch a.labels where a.id = :id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.close();
        return activity;
    }

    public Activity findActivityByIdWithTrackPoints(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Activity activity = entityManager
                .createQuery("select distinct a from Activity a left join fetch a.trackPoints where a.id = :id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.close();
        return activity;
    }

}
