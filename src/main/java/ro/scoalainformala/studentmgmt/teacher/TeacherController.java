package ro.scoalainformala.studentmgmt.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @GetMapping public List<TeacherDTO> getTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/id")
    public TeacherDTO getTeacherById(@PathVariable("id") final Long id) {
        return teacherService.getTeacherById(id);
    }

    @PostMapping("/add")
    public TeacherDTO createTeacher(@RequestBody final TeacherDTO teacherDTO) {
        return teacherService.createTeacher(teacherDTO);
    }

    @PutMapping("{id}")
    public TeacherDTO updateTeacher(@PathVariable("id") final Long id, @RequestBody final TeacherDTO teacherDTO) {
        return teacherService.updateTeacher(id, teacherDTO);
    }

    @DeleteMapping("{id}")
    public void deleteTeacher(@PathVariable("id") final Long id) {
        teacherService.deleteTeacher(id);
    }
}
