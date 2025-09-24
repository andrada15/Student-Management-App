package ro.scoalainformala.studentmgmt.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.scoalainformala.studentmgmt.courses.CourseDTO;
import ro.scoalainformala.studentmgmt.courses.CourseService;

import java.util.List;

@Controller
@RequestMapping("/web/assignments")
public class AssignmentWebController {

    private final AssignmentService assignmentService;
    private final CourseService courseService;

    @Autowired
    public AssignmentWebController(AssignmentService assignmentService, CourseService courseService) {
        this.assignmentService = assignmentService;
        this.courseService = courseService;
    }

    @GetMapping("/course/{courseId}")
    public String assignmentsByCourse(@PathVariable Long courseId, Model model) {
        List<AssignmentDTO> assignments = assignmentService.getAllAssignmentsByCourseId(courseId);
        model.addAttribute("assignments", assignments);
        model.addAttribute("courseId", courseId);
        return "/assignment/assignment-list";
    }

    @GetMapping("/{id}")
    public String viewAssignment(@PathVariable Long id, Model model) {
        AssignmentDTO assignment = assignmentService.getAssignmentById(id);
        model.addAttribute("assignment", assignment);
        return "/assignment/assignment-view";
    }

    @GetMapping
    public String getAllAssignments(Model model) {
        List<AssignmentDTO> assignments = assignmentService.getAllAssignments();
        model.addAttribute("assignments", assignments);
        return "/assignment/assignment-list";
    }

    @GetMapping("/create")
    public String createAssignmentForm(Model model) {
        List<CourseDTO> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "/assignment/assignment-create";
    }

    @GetMapping("/create/course/{courseId}")
    public String createAssignmentForCourse(@PathVariable Long courseId, Model model) {
        List<CourseDTO> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("courseId", courseId);
        return "/assignment/assignment-create";
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadAssignmentFile(@PathVariable Long id) {
        try {
            Resource file = assignmentService.downloadAssignment(id);
            AssignmentDTO assignmentDTO = assignmentService.getAssignmentById(id);

            if (file == null || !file.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + assignmentDTO.getOriginalFileName() + "\"")
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
