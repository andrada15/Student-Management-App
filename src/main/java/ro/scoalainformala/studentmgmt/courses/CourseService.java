package ro.scoalainformala.studentmgmt.courses;

import java.util.List;

public interface CourseService {

    List<CourseDTO> getAllCourses();
    CourseDTO getCourseById(Long id);
    CourseDTO addCourse(CourseDTO course);
    CourseDTO updateCourse(final Long id, CourseDTO course);
    void deleteCourse(Long id);
    CourseDTO getCourseByName(String courseName);
}
