package ro.scoalainformala.studentmgmt.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.scoalainformala.studentmgmt.courses.CourseDTO;
import ro.scoalainformala.studentmgmt.department.DepartmentEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer departmentId;
    private DepartmentEnum department;
    private CourseDTO course;
}
