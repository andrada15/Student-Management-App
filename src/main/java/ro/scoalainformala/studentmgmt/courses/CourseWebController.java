package ro.scoalainformala.studentmgmt.courses;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/web/courses")
public class CourseWebController {
    private final CourseWebServiceImpl courseWebService;

    @Autowired
    public CourseWebController(CourseWebServiceImpl courseWebService) {
        this.courseWebService = courseWebService;
    }

    @GetMapping
    public String courseListPage(Model model) {
        return courseWebService.courseListPage(model);
    }

    @GetMapping("/new")
    public String newCoursePage(Model model) {
        model.addAttribute("course", new CourseDTO());
        return "/course/course-form";
    }

    @PostMapping
    public String addCourse(@Valid @ModelAttribute("course") CourseDTO course, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Model model) {
       return courseWebService.addCourse(course, bindingResult, redirectAttributes, model);
    }

    @GetMapping("/{id}")
    public String viewCourse(@PathVariable Long id, Model model) {
        return courseWebService.viewCourse(id, model);
    }

    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable Long id, Model model) {
        return courseWebService.editCourse(id, model);
    }

    @PostMapping("/{id}")
    public String updateCourse(@PathVariable Long id, @Valid @ModelAttribute("course") CourseDTO course,
                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        return courseWebService.updateCourse(id, course, bindingResult, model, redirectAttributes);
    }

    @PostMapping("/{id}/delete")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseWebService.deleteCourse(id, redirectAttributes);
        return "redirect:/web/courses";
    }
}
