package ro.scoalainformala.studentmgmt.student;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface StudentWebService {
    String studentListPage(Model model);
    void newStudentPage(Model model);
    String addStudent(StudentDTO studentDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model);
    String viewStudent(Long id, Model model);
    String editStudent(Long id, Model model);
    String updateStudent(Long id, StudentDTO studentDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes);
    void deleteStudent(Long id, RedirectAttributes redirectAttributes);
}
