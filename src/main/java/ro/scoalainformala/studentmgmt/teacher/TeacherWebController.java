package ro.scoalainformala.studentmgmt.teacher;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/teachers")
public class TeacherWebController {

   private final TeacherWebService teacherWebService;

   @Autowired
    public TeacherWebController(TeacherWebService teacherWebService) {
        this.teacherWebService = teacherWebService;
    }

    @GetMapping
    public String teacherListPage(Model model) {
        return teacherWebService.teacherListPage(model);
    }

    @GetMapping("/new")
    public String newTeacherPage(Model model) {
        teacherWebService.newTeacherPage(model);
        return "/teacher/teacher-form";
    }

    @PostMapping
    public String addTeacher(@Valid @ModelAttribute("teacher") TeacherDTO teacher, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, Model model) {
        return teacherWebService.addTeacher(teacher, bindingResult, redirectAttributes, model);
    }

    @GetMapping("/{id}")
    public String viewTeacher(@PathVariable Long id, Model model) {
       return teacherWebService.viewTeacher(id, model);
    }

    @GetMapping("/{id}/edit")
    public String editTeacher(@PathVariable Long id, Model model) {
        return teacherWebService.editTeacher(id, model);
    }

    @PostMapping("/{id}")
    public String updateTeacher(@PathVariable Long id, @Valid @ModelAttribute("teacher") TeacherDTO teacher,
                                BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        return teacherWebService.updateTeacher(id, teacher, bindingResult, model, redirectAttributes);
    }

    @PostMapping("/{id}/delete")
    public String deleteTeacher(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        teacherWebService.deleteTeacher(id, redirectAttributes);
        return "redirect:/web/teachers";
    }
}
