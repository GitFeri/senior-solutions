package activitytracker;


import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.time.LocalDateTime;

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

        Flyway flyway = Flyway.configure()
//                .locations("/db/migration/activitytracker")
                .dataSource(dataSource)
                .load();
        flyway.clean();
        flyway.migrate();

        entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        activityDao = new ActivityDao(entityManagerFactory);
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
        Activity savedActivity5 = null;
        Activity savedActivity3 = null;

        for (int i = 10; i > 0; i--) {
            Activity activity = new Activity(LocalDateTime.of(2000, 1, 1, i, 0),
                    "Any" + i, ActivityType.BIKING);
            activityDao.saveActivity(activity);

            if (i == 5) {
                savedActivity5 = activityDao.findActivityById(activity.getId());
            }
            if (i == 3) {
                savedActivity3 = activityDao.findActivityById(activity.getId());
            }
        }

        assertEquals("Any3", savedActivity3.getDescription());
    }
}