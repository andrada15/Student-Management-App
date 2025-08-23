package ro.scoalainformala.studentmgmt.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDTO {

    private Long id;

    private String name;
    private String description;
    private List<Long> studentId;
    private Long courseId;
    private LocalDateTime startDate;
    private LocalDateTime expirationDate;
    private String originalFileName;
    private String courseName;
    private String additionalInfo;

}
