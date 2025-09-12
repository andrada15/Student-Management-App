package ro.scoalainformala.studentmgmt.grades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.scoalainformala.studentmgmt.courses.CourseDO;
import ro.scoalainformala.studentmgmt.student.StudentDO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="grades")
public class GradesDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Float grade;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentDO student;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseDO course;
}
