package ro.scoalainformala.studentmgmt.grades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradesController {

    private final GradesService gradesService;

    @Autowired
    public GradesController(GradesService gradesService) {
        this.gradesService = gradesService;
    }

    @GetMapping("{studentId}")
    public List<GradesDTO> getAllGradesByStudentId(@PathVariable("studentId") final long studentId) {
        return gradesService.getAllGradesByStudentId(studentId);
    }
}
