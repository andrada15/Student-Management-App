package ro.scoalainformala.studentmgmt.student;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.scoalainformala.studentmgmt.courses.CourseDTO;
import ro.scoalainformala.studentmgmt.courses.CourseService;

import java.util.List;

@Slf4j
@Service
public class StudentWebServiceImpl implements StudentWebService {
    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public StudentWebServiceImpl(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @Override
    public String studentListPage(Model model) {
        try {
            List<StudentDTO> students = studentService.getAllStudents();

            log.info("Students fetched: " + (students == null ? "null" : students.size()));

            if (students != null) {
                for (int i = 0; i < students.size(); i++) {
                    StudentDTO student = students.get(i);
                    log.info("Student " + i + ": " + (student != null ? student.toString() : "null"));
                }
            }

            List<StudentDTO> validStudents = students != null ?
                    students.stream()
                            .filter(student -> student != null &&
                                    student.getFirstName() != null && student.getLastName() != null)
                            .toList() :
                    List.of();

            model.addAttribute("students", validStudents);
            return "/student/student-list";
        } catch (Exception e) {
            log.error(e.getMessage());
            model.addAttribute("students", List.of());
            model.addAttribute("message", e.getMessage());
            return "/student/student-list";
        }
    }

    @Override
    public void newStudentPage(Model model) {
        model.addAttribute("student", new StudentDTO());
        try {
            List<CourseDTO> courses = courseService.getAllCourses();
            model.addAttribute("courses", courses != null ? courses : List.of());
        } catch (Exception e) {
            model.addAttribute("courses", List.of());
            model.addAttribute("message", e.getMessage());
        }
    }

    @Override
    public String addStudent(StudentDTO studentDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            try {
                List<CourseDTO> courses = courseService.getAllCourses();
                model.addAttribute("courses", courses != null ? courses : List.of());
            } catch (Exception e) {
                model.addAttribute("courses", List.of());
            }
            return "/student/student-form";
        }
        try {
            studentService.addStudent(studentDTO);
            redirectAttributes.addFlashAttribute("message", "Student added");
            return "redirect:/web/students";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            try {
                List<CourseDTO> courses = courseService.getAllCourses();
                model.addAttribute("courses", courses != null ? courses : List.of());
            } catch (Exception e2) {
                model.addAttribute("courses", List.of());
            }
            return "/student/student-form";
        }
    }

    @Override
    public String viewStudent(Long id, Model model) {
        try {
            StudentDTO student = studentService.getStudentById(id);
            if (student == null) {
                model.addAttribute("message", "Student not found");
                return "redirect:/web/students";
            }
            model.addAttribute("student", student);
            return "/student/student-view";
        } catch (Exception e) {
            model.addAttribute("message", "Error loading student: " + e.getMessage());
            return "redirect:/web/students";
        }
    }

    @Override
    public String editStudent(Long id, Model model) {
        try {
            StudentDTO student = studentService.getStudentById(id);
            if (student == null) {
                model.addAttribute("message", "Student not found");
                return "redirect:/web/students";
            }
            model.addAttribute("student", student);

            List<CourseDTO> courses = courseService.getAllCourses();
            model.addAttribute("courses", courses != null ? courses : List.of());
            return "/student/student-form";
        } catch (Exception e) {
            model.addAttribute("message", "Error loading student: " + e.getMessage());
            return "redirect:/web/students";
        }
    }

    @Override
    public String updateStudent(Long id, StudentDTO studentDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            try {
                List<CourseDTO> courses = courseService.getAllCourses();
                model.addAttribute("courses", courses != null ? courses : List.of());
            } catch (Exception e) {
                model.addAttribute("courses", List.of());
            }
            return "/student/student-form";
        }
        try {
            studentService.updateStudent(id, studentDTO);
            redirectAttributes.addFlashAttribute("message", "Student updated successfully");
            return "redirect:/web/students";
        } catch (Exception e) {
            model.addAttribute("message", "Error updating student: " + e.getMessage());
            try {
                List<CourseDTO> courses = courseService.getAllCourses();
                model.addAttribute("courses", courses != null ? courses : List.of());
            } catch (Exception ex) {
                model.addAttribute("courses", List.of());
            }
            return "/student/student-form";
        }
    }

    @Override
    public void deleteStudent(Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("message", "Student deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting student: " + e.getMessage());
        }
    }
}
