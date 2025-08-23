package ro.scoalainformala.studentmgmt.courses;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/web/courses")
public class CourseWebController {
    private final CourseService courseService;

    @Autowired
    public CourseWebController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String courseListPage(Model model) {
        try {
            List<CourseDTO> courses = courseService.getAllCourses();

            System.out.println("Courses fetched: " + (courses != null ? courses.size() : "null"));
            if (courses != null) {
                for (int i = 0; i < courses.size(); i++) {
                    CourseDTO course = courses.get(i);
                    System.out.println("Course " + i + ": " + (course != null ? course.toString() : "null"));
                }
            }

            List<CourseDTO> validCourses = courses != null ?
                    courses.stream()
                            .filter(course -> course != null &&
                                    course.getName() != null)
                            .collect(Collectors.toList()) :
                    List.of();

            model.addAttribute("courses", validCourses);
            return "/course/course-list";
        } catch (Exception e) {
            System.err.println("Error fetching courses: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("courses", List.of());
            model.addAttribute("message", "Error loading courses: " + e.getMessage());
            return "/course/course-list";
        }
    }

    @GetMapping("/new")
    public String newCoursePage(Model model) {
        model.addAttribute("course", new CourseDTO());
        return "/course/course-form";
    }

    @PostMapping
    public String addCourse(@Valid @ModelAttribute("course") CourseDTO course, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "/course/course-form";
        }
        try {
            courseService.addCourse(course);
            redirectAttributes.addFlashAttribute("message", "Course added successfully");
            return "redirect:/web/courses";
        } catch (Exception e) {
            model.addAttribute("message", "Error adding course: " + e.getMessage());
            return "/course/course-form";
        }
    }

    @GetMapping("/{id}")
    public String viewCourse(@PathVariable Long id, Model model) {
        try {
            CourseDTO course = courseService.getCourseById(id);
            if (course == null) {
                model.addAttribute("message", "Course not found");
                return "redirect:/web/courses";
            }
            model.addAttribute("course", course);
            return "/course/course-view";
        } catch (Exception e) {
            model.addAttribute("message", "Error loading course: " + e.getMessage());
            return "redirect:/web/courses";
        }
    }

    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable Long id, Model model) {
        try {
            CourseDTO course = courseService.getCourseById(id);
            if (course == null) {
                model.addAttribute("message", "Course not found");
                return "redirect:/web/courses";
            }
            model.addAttribute("course", course);
            return "/course/course-form";
        } catch (Exception e) {
            model.addAttribute("message", "Error loading course: " + e.getMessage());
            return "redirect:/web/courses";
        }
    }

    @PostMapping("/{id}")
    public String updateCourse(@PathVariable Long id, @Valid @ModelAttribute("course") CourseDTO course,
                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "/course/course-form";
        }
        try {
            courseService.updateCourse(id, course);
            redirectAttributes.addFlashAttribute("message", "Course updated successfully");
            return "redirect:/web/courses";
        } catch (Exception e) {
            model.addAttribute("message", "Error updating course: " + e.getMessage());
            return "/course/course-form";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("message", "Course deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting course: " + e.getMessage());
        }
        return "redirect:/web/courses";
    }
//    private final CourseService courseService;
//
//    @Autowired
//    public CourseWebController(CourseService courseService) {
//        this.courseService = courseService;
//    }
//
//    @GetMapping
//    public String courseListPage(Model model) {
//        List<CourseDTO> courses = courseService.getAllCourses();
//        model.addAttribute("courses", courses);
//        return "/course/course-list";
//    }
//
//    @GetMapping("/new")
//    public String newCoursePage(Model model) {
//        model.addAttribute("course", new CourseDTO());
//        return "/course/course-form";
//    }
//
//    @PostMapping
//    public String addCourse(@Valid @ModelAttribute("course") CourseDTO course, BindingResult bindingResult,
//                            RedirectAttributes redirectAttributes, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "/course/course-form";
//        }
//        try {
//            courseService.addCourse(course);
//            redirectAttributes.addFlashAttribute("message", "Course added successfully");
//            return "redirect:/web/courses";
//        } catch (Exception e) {
//            model.addAttribute("message", e.getMessage());
//            return "/course/course-form";
//        }
//    }
//
//    @GetMapping("/{id}")
//    public String viewCourse(@PathVariable Long id, Model model) {
//        CourseDTO course = courseService.getCourseById(id);
//        model.addAttribute("course", course);
//        return "/course/course-view";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String editCourse(@PathVariable Long id, Model model) {
//        CourseDTO course = courseService.getCourseById(id);
//        model.addAttribute("course", course);
//        return "/course/course-form";
//    }
//
//    @PostMapping("/{id}")
//    public String updateCourse(@PathVariable Long id, @Valid @ModelAttribute("course") CourseDTO course,
//                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            return "/course/course-form";
//        }
//        try {
//            courseService.addCourse(course);
//            redirectAttributes.addFlashAttribute("message", "Course updated successfully");
//            return "redirect:/web/courses";
//        } catch (Exception e) {
//            model.addAttribute("message", e.getMessage());
//            return "/course/course-form";
//        }
//    }
//
//    @PostMapping("/{id}/delete")
//    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//        courseService.deleteCourse(id);
//        redirectAttributes.addFlashAttribute("message", "Course deleted successfully");
//        return "redirect:/web/courses";
//    }
}
