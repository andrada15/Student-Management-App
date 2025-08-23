package ro.scoalainformala.studentmgmt.assignment;


import jakarta.persistence.*;
import ro.scoalainformala.studentmgmt.student.StudentDO;

import java.time.LocalDateTime;

@Entity(name = "assignment_submissions")
public class AssignmentSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private AssignmentDO assignment;

    @ManyToOne
    private StudentDO student;

    private String submissionFileName;
    private LocalDateTime submissionTime;
}
