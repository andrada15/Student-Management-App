package ro.scoalainformala.studentmgmt.courses;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface CourseWebService {

    String courseListPage(Model model);
    String newCoursePage(Model model);
    String addCourse(CourseDTO courseDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model);
    String viewCourse(Long id, Model model);
    String editCourse(Long id, Model model);
    String updateCourse(Long id, CourseDTO courseDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes);
    void deleteCourse(Long id, RedirectAttributes redirectAttributes);
}
