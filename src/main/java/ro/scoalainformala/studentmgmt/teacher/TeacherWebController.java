package ro.scoalainformala.studentmgmt.teacher;

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
@RequestMapping("/web/teachers")
public class TeacherWebController {

    private final TeacherService teacherService;
    private final CourseService courseService;

    @Autowired
    public TeacherWebController(TeacherService teacherService, CourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
    }
    @GetMapping
    public String teacherListPage(Model model) {
        try {
            List<TeacherDTO> teachers = teacherService.getAllTeachers();

            System.out.println("Teachers fetched: " + (teachers != null ? teachers.size() : "null"));
            if (teachers != null) {
                for (int i = 0; i < teachers.size(); i++) {
                    TeacherDTO teacher = teachers.get(i);
                    System.out.println("Teacher " + i + ": " + (teacher != null ? teacher.toString() : "null"));
                }
            }

            List<TeacherDTO> validTeachers = teachers != null ?
                    teachers.stream()
                            .filter(teacher -> teacher != null &&
                                    teacher.getFirstName() != null &&
                                    teacher.getLastName() != null)
                            .collect(Collectors.toList()) :
                    List.of();

            model.addAttribute("teachers", validTeachers);
            return "/teacher/teacher-list";
        } catch (Exception e) {
            System.err.println("Error fetching teachers: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("teachers", List.of());
            model.addAttribute("message", "Error loading teachers: " + e.getMessage());
            return "/teacher/teacher-list";
        }
    }

    @GetMapping("/new")
    public String newTeacherPage(Model model) {
        model.addAttribute("teacher", new TeacherDTO());
        try {
            List<CourseDTO> courses = courseService.getAllCourses();
            model.addAttribute("courses", courses != null ? courses : List.of());
        } catch (Exception e) {
            model.addAttribute("courses", List.of());
            model.addAttribute("message", "Error loading courses: " + e.getMessage());
        }
        return "/teacher/teacher-form";
    }

    @PostMapping
    public String addTeacher(@Valid @ModelAttribute("teacher") TeacherDTO teacher, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            try {
                List<CourseDTO> courses = courseService.getAllCourses();
                model.addAttribute("courses", courses != null ? courses : List.of());
            } catch (Exception e) {
                model.addAttribute("courses", List.of());
            }
            return "/teacher/teacher-form";
        }
        try {
            teacherService.createTeacher(teacher);
            redirectAttributes.addFlashAttribute("message", "Teacher added successfully");
            return "redirect:/web/teachers";
        } catch (Exception e) {
            model.addAttribute("message", "Error adding teacher: " + e.getMessage());
            try {
                List<CourseDTO> courses = courseService.getAllCourses();
                model.addAttribute("courses", courses != null ? courses : List.of());
            } catch (Exception ex) {
                model.addAttribute("courses", List.of());
            }
            return "/teacher/teacher-form";
        }
    }

    @GetMapping("/{id}")
    public String viewTeacher(@PathVariable Long id, Model model) {
        try {
            TeacherDTO teacher = teacherService.getTeacherById(id);
            if (teacher == null) {
                model.addAttribute("message", "Teacher not found");
                return "redirect:/web/teachers";
            }
            model.addAttribute("teacher", teacher);
            return "/teacher/teacher-view";
        } catch (Exception e) {
            model.addAttribute("message", "Error loading teacher: " + e.getMessage());
            return "redirect:/web/teachers";
        }
    }

    @GetMapping("/{id}/edit")
    public String editTeacher(@PathVariable Long id, Model model) {
        try {
            TeacherDTO teacher = teacherService.getTeacherById(id);
            if (teacher == null) {
                model.addAttribute("message", "Teacher not found");
                return "redirect:/web/teachers";
            }
            model.addAttribute("teacher", teacher);

            List<CourseDTO> courses = courseService.getAllCourses();
            model.addAttribute("courses", courses != null ? courses : List.of());
            return "/teacher/teacher-form";
        } catch (Exception e) {
            model.addAttribute("message", "Error loading teacher: " + e.getMessage());
            return "redirect:/web/teachers";
        }
    }

    @PostMapping("/{id}")
    public String updateTeacher(@PathVariable Long id, @Valid @ModelAttribute("teacher") TeacherDTO teacher,
                                BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            try {
                List<CourseDTO> courses = courseService.getAllCourses();
                model.addAttribute("courses", courses != null ? courses : List.of());
            } catch (Exception e) {
                model.addAttribute("courses", List.of());
            }
            return "/teacher/teacher-form";
        }
        try {
            teacherService.updateTeacher(id, teacher);
            redirectAttributes.addFlashAttribute("message", "Teacher updated successfully");
            return "redirect:/web/teachers";
        } catch (Exception e) {
            model.addAttribute("message", "Error updating teacher: " + e.getMessage());
            try {
                List<CourseDTO> courses = courseService.getAllCourses();
                model.addAttribute("courses", courses != null ? courses : List.of());
            } catch (Exception ex) {
                model.addAttribute("courses", List.of());
            }
            return "/teacher/teacher-form";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteTeacher(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            teacherService.deleteTeacher(id);
            redirectAttributes.addFlashAttribute("message", "Teacher deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting teacher: " + e.getMessage());
        }
        return "redirect:/web/teachers";
    }
}
