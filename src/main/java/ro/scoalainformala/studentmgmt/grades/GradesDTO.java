package ro.scoalainformala.studentmgmt.grades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradesDTO {

    private Long id;
    private float grade;
    private Long studentId;
    private Long courseId;
}
