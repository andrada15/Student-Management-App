package ro.scoalainformala.studentmgmt.teacher;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface TeacherWebService {
    String teacherListPage(Model model);
    void newTeacherPage(Model model);
    String addTeacher(TeacherDTO teacher, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model);
    String viewTeacher(Long id, Model model);
    String editTeacher(Long id, Model model);
    String updateTeacher(Long id, TeacherDTO teacher, BindingResult bindingResult, Model model,  RedirectAttributes redirectAttributes);
    void deleteTeacher(Long id, RedirectAttributes redirectAttributes);
}
