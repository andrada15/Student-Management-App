package ro.scoalainformala.studentmgmt.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(final CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDTO> getCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("{id}")
    public CourseDTO getCourseById(@PathVariable("id") final Long id) {
        return courseService.getCourseById(id);
    }


    @PostMapping("/add")
    public CourseDTO addCourse(@RequestBody final CourseDTO course) {
        return courseService.addCourse(course);
    }

    @PostMapping("/update")
    public CourseDTO updateCourse(@PathVariable("id") final Long id, @RequestBody final CourseDTO course) {
        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("{id}")
    public void deleteCourseById(@PathVariable("id") final Long id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("{name}")
    public CourseDTO getCourseByName(@PathVariable("name") String name) {
        return courseService.getCourseByName(name);
    }
}
