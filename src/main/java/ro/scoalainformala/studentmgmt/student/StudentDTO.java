package ro.scoalainformala.studentmgmt.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.scoalainformala.studentmgmt.courses.CourseDTO;
import ro.scoalainformala.studentmgmt.department.DepartmentEnum;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

    @Min(0)
    @Max(3)
    private Integer departmentId;

    @JsonProperty("department")
    private DepartmentEnum department;

    private Integer collegeYear;
    private List<CourseDTO> courses;
}
