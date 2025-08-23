package ro.scoalainformala.studentmgmt.student;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.scoalainformala.studentmgmt.courses.CourseDTO;
import ro.scoalainformala.studentmgmt.courses.CourseService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/web/students")
public class StudentWebController {

    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public StudentWebController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }
    @GetMapping
    public String studentListPage(Model model) {
        try {
            List<StudentDTO> students = studentService.getAllStudents();

            System.out.println("Students fetched: " + (students != null ? students.size() : "null"));
            if (students != null) {
                for (int i = 0; i < students.size(); i++) {
                    StudentDTO student = students.get(i);
                    System.out.println("Student " + i + ": " + (student != null ? student.toString() : "null"));
                }
            }

            List<StudentDTO> validStudents = students != null ?
                    students.stream()
                            .filter(student -> student != null &&
                                    student.getFirstName() != null &&
                                    student.getLastName() != null)
                            .collect(Collectors.toList()) :
                    List.of();

            model.addAttribute("students", validStudents);
            return "/student/student-list";
        } catch (Exception e) {
            System.err.println("Error fetching students: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("students", List.of());
            model.addAttribute("message", "Error loading students: " + e.getMessage());
            return "/student/student-list";
        }
    }

    @GetMapping("/new")
    public String newStudentPage(Model model) {
        model.addAttribute("student", new StudentDTO());
        try {
            List<CourseDTO> courses = courseService.getAllCourses();
            model.addAttribute("courses", courses != null ? courses : List.of());
        } catch (Exception e) {
            model.addAttribute("courses", List.of());
            model.addAttribute("message", "Error loading courses: " + e.getMessage());
        }
        return "/student/student-form";
    }

    @PostMapping
    public String addStudent(@Valid @ModelAttribute("student") StudentDTO student, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, Model model) {
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
            studentService.addStudent(student);
            redirectAttributes.addFlashAttribute("message", "Student added successfully");
            return "redirect:/web/students";
        } catch (Exception e) {
            model.addAttribute("message", "Error adding student: " + e.getMessage());
            try {
                List<CourseDTO> courses = courseService.getAllCourses();
                model.addAttribute("courses", courses != null ? courses : List.of());
            } catch (Exception ex) {
                model.addAttribute("courses", List.of());
            }
            return "/student/student-form";
        }
    }

    @GetMapping("/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
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

    @GetMapping("/{id}/edit")
    public String editStudent(@PathVariable Long id, Model model) {
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

    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id, @Valid @ModelAttribute("student") StudentDTO student,
                                BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
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
            studentService.updateStudent(id, student);
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
            return "student-form";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("message", "Student deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting student: " + e.getMessage());
        }
        return "redirect:/web/students";
    }
}

