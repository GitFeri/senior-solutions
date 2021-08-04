package activitytracker;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activities")
@Data
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "table-generator")
    @TableGenerator(name = "table-generator",
            table = "act_id_gen",
            pkColumnName = "id_gen",
            valueColumnName = "id_val")
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(length = 200, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ActivityType type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ElementCollection
    @Column(name = "label")
    private List<String> labels;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.PERSIST)
    @OrderBy("time")
    private List<TrackPoint> trackPoints = new ArrayList<>();

    public Activity(LocalDateTime startTime, String description, ActivityType type) {
        this.startTime = startTime;
        this.description = description;
        this.type = type;
    }

    public void addTrackPoint(TrackPoint trackPoint) {
        trackPoint.setActivity(this);
        trackPoints.add(trackPoint);
    }

    @PrePersist
    private void setCreated() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    private void SetUpdated() {
        updatedAt = LocalDateTime.now();
    }

}
