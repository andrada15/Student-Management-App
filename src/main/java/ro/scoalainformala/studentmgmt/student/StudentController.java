package ro.scoalainformala.studentmgmt.student;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentController(StudentService studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @GetMapping("/profile")
    public ResponseEntity<StudentDTO> getStudentProfile(final Authentication auth) {
        final StudentDO student = studentService.getStudentByEmail(auth.getName());

        return ResponseEntity.ok(studentMapper.mapDoToDto(student));
    }

    @PostMapping("/add")
    public StudentDTO addStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.addStudent(studentDTO);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable("id") final Long id) {
        studentService.deleteStudent(id);
        log.info("Student with id {} was deleted", id);
    }

    @PutMapping("{id}")
    public StudentDTO updateStudent(@PathVariable("id") final Long studentId, @RequestBody StudentDTO studentDTO) {
        return studentService.updateStudent(studentId, studentDTO);
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable("id") final Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping
    public List<StudentDTO> getStudents() {
        return studentService.getAllStudents();
    }
}
