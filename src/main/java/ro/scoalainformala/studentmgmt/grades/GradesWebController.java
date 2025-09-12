package ro.scoalainformala.studentmgmt.grades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web/grades")
public class GradesWebController {

    private final GradesService gradesService;

    @Autowired
    public GradesWebController(GradesService gradesService) {
        this.gradesService = gradesService;
    }

    @GetMapping("/student/{studentId}")
    public String viewGradesByStudent(@PathVariable Long studentId, Model model) {
        List<GradesDTO> grades = gradesService.getAllGradesByStudentId(studentId);
        model.addAttribute("grades", grades);
        return "/grades/grades-list";
    }
}
