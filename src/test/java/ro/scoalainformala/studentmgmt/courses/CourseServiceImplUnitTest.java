package ro.scoalainformala.studentmgmt.courses;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ro.scoalainformala.studentmgmt.student.StudentDO;
import ro.scoalainformala.studentmgmt.teacher.TeacherDO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseServiceImplUnitTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeAll
    void beforeAll() {
        openMocks(this);
    }

    @Test
    void getAllCourses() {
        final List<CourseDTO> courses = List.of(
                CourseDTO.builder().id(1L).name("Course1").student(Set.of(StudentDO.builder()
                        .firstName("firstName").build())).teacher(Set.of(TeacherDO.builder().firstName("Teacher Name").build())).build(),
                CourseDTO.builder().id(2L).name("Course2").student(Set.of(StudentDO.builder()
                        .firstName("firstName2").build())).teacher(Set.of(TeacherDO.builder().firstName("Teacher Name2").build())).build());
        final List<CourseDO> coursesDo = List.of(
                CourseDO.builder().id(1L).name("Course1").students(Set.of(StudentDO.builder()
                        .firstName("firstName").build())).teacher(Set.of(TeacherDO.builder().firstName("Teacher Name").build())).build(),
                CourseDO.builder().id(2L).name("Course2").students(Set.of(StudentDO.builder()
                        .firstName("firstName2").build())).teacher(Set.of(TeacherDO.builder().firstName("Teacher Name2").build())).build());

        when(courseRepository.findAll()).thenReturn(coursesDo);
        when(courseMapper.mapDoListToDto(coursesDo)).thenReturn(courses);

        final List<CourseDTO> courseDTOs = courseService.getAllCourses();

        assertEquals(courses, courseDTOs);
        verify(courseRepository).findAll();
        verify(courseMapper).mapDoListToDto(coursesDo);
        verifyNoMoreInteractions(courseRepository, courseMapper);

    }

    @Test
    void getCourseById() {
        final long id = 1L;
        final CourseDTO course = CourseDTO.builder().id(id).build();
        final CourseDO courseDO = CourseDO.builder().id(id).build();

        when(courseRepository.findById(id)).thenReturn(Optional.of(courseDO));
        when(courseMapper.mapDoToDto(courseDO)).thenReturn(course);

        final CourseDTO courseDTO = courseService.getCourseById(id);

        assertEquals(course, courseDTO);
        verify(courseRepository).findById(id);
        verify(courseMapper).mapDoToDto(courseDO);
        verifyNoMoreInteractions(courseRepository, courseMapper);
    }

    @Test
    void addCourse() {
       CourseDTO courseDTO = CourseDTO.builder().id(1L).name("Course1").student(Set.of(StudentDO.builder()
                .firstName("firstName").build())).teacher(Set.of(TeacherDO.builder().firstName("Teacher Name").build())).build();
       CourseDO courseDO = CourseDO.builder().id(1L).name("Course1").students(Set.of(StudentDO.builder()
                .firstName("firstName").build())).teacher(Set.of(TeacherDO.builder().firstName("Teacher Name").build())).build();
        CourseDTO savedCourseDTO = CourseDTO.builder().id(1L).name("Course1").student(Set.of(StudentDO.builder()
                .firstName("firstName").build())).teacher(Set.of(TeacherDO.builder().firstName("Teacher Name").build())).build();
        CourseDO savedCourseDO = CourseDO.builder().id(1L).name("Course1").students(Set.of(StudentDO.builder()
                .firstName("firstName").build())).teacher(Set.of(TeacherDO.builder().firstName("Teacher Name").build())).build();

        when(courseMapper.mapDtoToDo(courseDTO)).thenReturn(courseDO);
        when(courseRepository.save(courseDO)).thenReturn(savedCourseDO);
        when(courseMapper.mapDoToDto(savedCourseDO)).thenReturn(savedCourseDTO);

        final CourseDTO result = courseService.addCourse(courseDTO);

        assertEquals(savedCourseDTO, result);
        verify(courseRepository).save(savedCourseDO);
        verify(courseMapper).mapDoToDto(savedCourseDO);
        verifyNoMoreInteractions(courseRepository, courseMapper);

    }

    @Test
    void updateCourse() {
        final ArgumentCaptor<CourseDO> captor = ArgumentCaptor.forClass(CourseDO.class);
        final CourseDO courseDO = CourseDO.builder().id(1L).name("Course1").students(Set.of(StudentDO.builder()
                .firstName("firstName").build())).teacher(Set.of(TeacherDO.builder().firstName("Teacher Name").build())).build();

        final CourseDTO updatedCourseDTO = CourseDTO.builder().id(1L).name("Updated Course1").student(Set.of(StudentDO.builder()
                .firstName("firstName").build())).teacher(Set.of(TeacherDO.builder().firstName("Teacher Name").build())).build();

        final CourseDO expectedCourseDO = CourseDO.builder().id(1L).name("Course1").students(Set.of(StudentDO.builder()
                .firstName("firstName").build())).teacher(Set.of(TeacherDO.builder().firstName("Teacher Name").build())).build();

        when(courseRepository.findById(1L)).thenReturn(Optional.ofNullable(courseDO));
        when(courseRepository.save(captor.capture())).thenReturn(expectedCourseDO);
        when(courseMapper.mapDoToDto(expectedCourseDO)).thenReturn(updatedCourseDTO);

        final CourseDTO result = courseService.updateCourse(1L, updatedCourseDTO);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(updatedCourseDTO);
        verify(courseRepository).save(expectedCourseDO);
        verify(courseMapper).mapDoToDto(expectedCourseDO);
        verifyNoMoreInteractions(courseRepository, courseMapper);


    }

    @Test
    void deleteCourse() {
        final CourseDTO course = CourseDTO.builder().id(1L).name("name").build();

        courseService.deleteCourse(course.getId());

        verify(courseRepository).deleteById(course.getId());
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    void getCourseByName() {
        final String name = "Course1";
        final CourseDTO course = CourseDTO.builder().id(1L).name(name).build();
        final CourseDO courseDO = CourseDO.builder().id(1L).name(name).build();

        when(courseRepository.findByName(name)).thenReturn(courseDO);
        when(courseMapper.mapDoToDto(courseDO)).thenReturn(course);

        final CourseDTO courseDTO = courseService.getCourseByName(name);

        assertEquals(course, courseDTO);
        verify(courseRepository).findByName(name);
        verify(courseMapper).mapDoToDto(courseDO);
        verifyNoMoreInteractions(courseRepository, courseMapper);
    }
}