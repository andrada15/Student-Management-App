package ro.scoalainformala.studentmgmt.quartzconfig;

import jakarta.persistence.*;
import lombok.*;
import ro.scoalainformala.studentmgmt.assignment.AssignmentDO;
import ro.scoalainformala.studentmgmt.student.StudentDO;

import java.time.LocalDateTime;

@Data
@Entity(name = "jobs")
public class JobRequest extends BaseJobRequest {

    private LocalDateTime triggerTime;
    private String studentEmail;
    private String assignmentName;
    private LocalDateTime dueDate;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private AssignmentDO assignment;

    public static JobRequest fromAssignment(AssignmentDO assignmentDO, StudentDO studentDO) {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setTriggerTime(assignmentDO.getExpirationDate().minusHours(24));
        jobRequest.setStudentEmail(studentDO.getEmail());
        jobRequest.setAssignmentName(assignmentDO.getName());
        jobRequest.setDueDate(assignmentDO.getExpirationDate());
        jobRequest.setName("assign-reminder-" + assignmentDO.getId() + "-" + studentDO.getId());
        jobRequest.setGroup("assignment-reminders");
        return jobRequest;
    }
}
