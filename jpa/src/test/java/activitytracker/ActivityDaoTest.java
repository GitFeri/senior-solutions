package activitytracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActivityDaoTest {

    private EntityManagerFactory entityManagerFactory;
    private ActivityDao activityDao;

    @BeforeEach
    public void init() throws SQLException {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        dataSource.setUrl("jdbc:mariadb://localhost:3306/activitytracker?useUnicode=true");
        dataSource.setUser("activitytracker");
        dataSource.setPassword("activitytracker");

        entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        activityDao = new ActivityDao(entityManagerFactory);
    }

    @AfterEach
    public void done() {
        entityManagerFactory.close();
    }

    @Test
    void testSaveAndFindActivity() {
        Activity activity = new Activity(LocalDateTime.of(2000, 1, 1, 0, 0),
                "Any", ActivityType.BIKING);
        activityDao.saveActivity(activity);

        Activity savedActivity = activityDao.findActivityById(activity.getId());
        assertEquals("Any", savedActivity.getDescription());
    }

    @Test
    void testListActivities() {
        for (int i = 10; i > 0; i--) {
            Activity activity = new Activity(LocalDateTime.of(2000, 1, 1, i, 0),
                    "Any" + i, ActivityType.BIKING);
            activityDao.saveActivity(activity);
        }

        List<Activity> activities = activityDao.listActivities();
        assertEquals(10, activities.size());
        assertEquals("Any10", activities.get(1).getDescription());
        assertEquals("Any5", activities.get(5).getDescription());
    }


    @Test
    void testUpdateActivity() {
        for (int i = 10; i > 0; i--) {
            Activity activity = new Activity(LocalDateTime.of(2000, 1, 1, i, 0),
                    "Any" + i, ActivityType.BIKING);
            activityDao.saveActivity(activity);
        }

        activityDao.updateActivity(1, "Changed");

        assertEquals("Changed", activityDao.findActivityById(1).getDescription());

    }

    @Test
    void testFindActivityByIdWithLabels() {
        for (int i = 10; i > 0; i--) {
            Activity activity = new Activity(LocalDateTime.of(2000, 1, 1, i, 0),
                    "Any" + i, ActivityType.BIKING);
            activity.setLabels(List.of("Első", "Második " + i));
            activityDao.saveActivity(activity);
        }

        assertEquals("Második 5", activityDao.findActivityByIdWithLabels(6).getLabels().get(1));
    }

    @Test
    void testFindActivityByIdWithTrackPoints() {
        for (int i = 10; i > 0; i--) {
            Activity activity = new Activity(LocalDateTime.of(2000, 1, 1, i, 0),
                    "Any" + i, ActivityType.BIKING);
            activity.addTrackPoint(new TrackPoint(LocalDate.of(2021, 8, i), 19.1111, 40.1111));
            activity.addTrackPoint(new TrackPoint(LocalDate.of(2021, 7, i), 19.2222, 40.2222));
            activity.addTrackPoint(new TrackPoint(LocalDate.of(2021, 6, i), 19.3333, 40.3333));
            activityDao.saveActivity(activity);
        }

        assertEquals(19.3333, activityDao.findActivityByIdWithTrackPoints(6).getTrackPoints().get(0).getLat());
    }

}