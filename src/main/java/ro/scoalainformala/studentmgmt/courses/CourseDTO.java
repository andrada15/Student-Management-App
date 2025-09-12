package ro.scoalainformala.studentmgmt.courses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.scoalainformala.studentmgmt.department.DepartmentEnum;
import ro.scoalainformala.studentmgmt.student.StudentDO;
import ro.scoalainformala.studentmgmt.teacher.TeacherDO;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    private Long id;
    private String name;
    private Set<TeacherDO> teacher;
    private Set<StudentDO> student;
    private Integer departmentId;
    private DepartmentEnum department;
}
