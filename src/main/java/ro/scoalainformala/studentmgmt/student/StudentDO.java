package ro.scoalainformala.studentmgmt.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ro.scoalainformala.studentmgmt.assignment.AssignmentDO;
import ro.scoalainformala.studentmgmt.courses.CourseDO;
import ro.scoalainformala.studentmgmt.grades.GradesDO;
import ro.scoalainformala.studentmgmt.user.UserDO;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="students")
public class StudentDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer departmentId;

    @Column(nullable = false)
    private Integer collegeYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CourseDO course;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<AssignmentDO> assignment;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<GradesDO> grades;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserDO user;
}
