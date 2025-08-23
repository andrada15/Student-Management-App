package ro.scoalainformala.studentmgmt.courses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ro.scoalainformala.studentmgmt.assignment.AssignmentDO;
import ro.scoalainformala.studentmgmt.grades.GradesDO;
import ro.scoalainformala.studentmgmt.student.StudentDO;
import ro.scoalainformala.studentmgmt.teacher.TeacherDO;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "course")
public class CourseDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TeacherDO> teacher;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<StudentDO> students;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<AssignmentDO> assignment;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<GradesDO> grades;

    private Integer departmentId;


}
