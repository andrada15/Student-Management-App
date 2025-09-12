package ro.scoalainformala.studentmgmt.courses;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ro.scoalainformala.studentmgmt.student.StudentDO;
import ro.scoalainformala.studentmgmt.teacher.TeacherDO;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseControllerUnitTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @BeforeAll
    void beforeAll() {
        openMocks(this);
    }

    @Test
    void testGetCourses() {
        final List<CourseDTO> courses = List.of(
                CourseDTO.builder().id(1L).name("Course1").student(Set.of(StudentDO.builder()
                        .firstName("firstName").build())).teacher(Set.of(TeacherDO.builder().firstName("Teacher Name").build())).build(),
                CourseDTO.builder().id(2L).name("Course2").student(Set.of(StudentDO.builder()
                        .firstName("firstName2").build())).teacher(Set.of(TeacherDO.builder().firstName("Teacher Name2").build())).build()

        );

        when (courseService.getAllCourses()).thenReturn(courses);

        final List<CourseDTO> coursesDTO = courseController.getCourses();

        assertEquals(coursesDTO, courses);
        verify(courseService).getAllCourses();
        verifyNoMoreInteractions(courseService);
    }

    @Test
    void testGetCourseById() {
        final long id = 1L;
        final CourseDTO course = CourseDTO.builder().id(id).build();
        when (courseService.getCourseById(id)).thenReturn(course);
        final CourseDTO courseDTO = courseController.getCourseById(id);

        assertEquals(courseDTO, course);
        verify(courseService).getCourseById(id);
        verifyNoMoreInteractions(courseService);
    }

    @Test
    void testAddCourse() {
        final CourseDTO courseDTO = CourseDTO.builder().id(1L).build();

        when (courseService.addCourse(courseDTO)).thenReturn(courseDTO);

        final CourseDTO courseDTOAdded = courseController.addCourse(courseDTO);

        assertEquals(courseDTOAdded, courseDTO);
        verify(courseService).addCourse(courseDTO);
        verifyNoMoreInteractions(courseService);
    }

    @Test
    void testDeleteCourseById() {
        final CourseDTO courseDTO = CourseDTO.builder().id(1L).build();

        courseController.deleteCourseById(courseDTO.getId());

        verify(courseService).deleteCourse(1L);
        verifyNoMoreInteractions(courseService);


    }

    @Test
    void testGetCourseByName() {
        final CourseDTO courseDTO = CourseDTO.builder().id(1L).name("Course1").build();

        when (courseService.getCourseByName(courseDTO.getName())).thenReturn(courseDTO);

        final CourseDTO courseDTOByName = courseController.getCourseByName(courseDTO.getName());

        assertEquals(courseDTOByName, courseDTO);
        verify(courseService).getCourseByName(courseDTO.getName());
        verifyNoMoreInteractions(courseService);
    }
}