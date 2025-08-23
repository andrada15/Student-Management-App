package ro.scoalainformala.studentmgmt.assignment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ro.scoalainformala.studentmgmt.courses.CourseDO;
import ro.scoalainformala.studentmgmt.quartzconfig.JobRequest;
import ro.scoalainformala.studentmgmt.student.StudentDO;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "assignment")
public class AssignmentDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime expirationDate;
    private String storedFileName;
    private String originalFileName;
    private String additionalInfo;

    @ManyToMany
    @JoinTable(
            name = "student_assignments",
            joinColumns = @JoinColumn(name = "assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<StudentDO> students = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CourseDO course;

    @OneToMany(
            mappedBy = "assignment",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<JobRequest> scheduledJobs = new HashSet<>();

    public void scheduleReminders() {
        for (StudentDO student : students) {
            scheduledJobs.add(JobRequest.fromAssignment(this, student));
        }
    }
}
