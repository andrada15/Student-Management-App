package ro.scoalainformala.studentmgmt.courses;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseWebServiceImpl implements CourseWebService {

    private final CourseService courseService;

    @Autowired
    public CourseWebServiceImpl(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public String courseListPage(Model model) {
        try {
            List<CourseDTO> courses = courseService.getAllCourses();
            log.info("Courses fetched:{}", courses == null ? "null" : courses.size());

            if (courses != null) {
                for (int i = 0; i < courses.size(); i++) {
                    CourseDTO course = courses.get(i);
                    log.info("Course {}: {}", i, course != null ? course.toString() : "null");
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
            log.error(e.getMessage());
            model.addAttribute("courses", List.of());
            model.addAttribute("message", "Error loading courses: " + e.getMessage());
            return "/course/course-list";
        }
    }

    @Override
    public String newCoursePage(Model model) {
        model.addAttribute("course", new CourseDTO());
        return "/course/course-form";
    }

    @Override
    public String addCourse(CourseDTO courseDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "/course/course-form";
        }
        try {
            courseService.addCourse(courseDTO);
            redirectAttributes.addFlashAttribute("message", "Course added successfully");
            return "redirect:/web/courses";
        } catch (Exception e) {
            model.addAttribute("message", "Error adding course to courses: " + e.getMessage());
            return "/course/course-form";
        }
    }

    @Override
    public String viewCourse(Long id, Model model) {
        try {
            CourseDTO course = courseService.getCourseById(id);
            if (course == null) {
                model.addAttribute("message", "Course not found");
                return "redirect:/web/courses";
            }
            model.addAttribute("course", course);
            return "/course/course-view";
        } catch (Exception e) {
            model.addAttribute("message", "Error viewing course: " + e.getMessage());
            return "redirect:/web/courses";
        }
    }

    @Override
    public String editCourse(Long id, Model model) {
        try {
            CourseDTO course = courseService.getCourseById(id);
            if (course == null) {
                model.addAttribute("message", "Course not found");
                return "redirect:/web/courses";
            }
            model.addAttribute("course", course);
            return "/course/course-form";
        } catch (Exception e) {
            model.addAttribute("message", "Error editing course: " + e.getMessage());
            return "redirect:/web/courses";
        }
    }

    @Override
    public String updateCourse(Long id, CourseDTO courseDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "/course/course-form";
        }
        try {
            courseService.updateCourse(id, courseDTO);
            redirectAttributes.addFlashAttribute("message", "Course updated successfully");
            return "redirect:/web/courses";
        } catch (Exception e) {
            model.addAttribute("message", "Error updating course: " + e.getMessage());
            return "/course/course-form";
        }
    }

    @Override
    public void deleteCourse(Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("message", "Course deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting course: " + e.getMessage());
        }
    }
}
