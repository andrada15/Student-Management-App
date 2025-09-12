package ro.scoalainformala.studentmgmt.student;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/students")
public class StudentWebController {

    private final StudentWebServiceImpl studentWebService;

    @Autowired
    public StudentWebController(StudentWebServiceImpl studentWebService) {
        this.studentWebService = studentWebService;
    }

    @GetMapping
    public String studentListPage(Model model) {
       return studentWebService.studentListPage(model);
    }

    @GetMapping("/new")
    public String newStudentPage(Model model) {
        studentWebService.newStudentPage(model);
        return "/student/student-form";
    }

    @PostMapping
    public String addStudent(@Valid @ModelAttribute("student") StudentDTO student, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, Model model) {
        return studentWebService.addStudent(student, bindingResult, redirectAttributes, model);
    }

    @GetMapping("/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        return studentWebService.viewStudent(id, model);
    }

    @GetMapping("/{id}/edit")
    public String editStudent(@PathVariable Long id, Model model) {
        return studentWebService.editStudent(id, model);
    }

    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id, @Valid @ModelAttribute("student") StudentDTO student,
                                BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        return studentWebService.updateStudent(id, student, bindingResult, model, redirectAttributes);
    }

    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentWebService.deleteStudent(id, redirectAttributes);
        return "redirect:/web/students";
    }
}

