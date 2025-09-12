package ro.scoalainformala.studentmgmt.student;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentControllerUnitTest {

    @Mock
    private StudentService studentService;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private Authentication authentication;

    @Spy
    @InjectMocks
    private StudentController studentController;

    @BeforeAll
    void beforeAll() {
        openMocks(this);
    }

    @Test
    void testGetStudentProfile() {
        StudentDO studentDO = StudentDO.builder().id(1L).email("john@example.com").build();
        StudentDTO studentDTO = StudentDTO.builder().id(1L).email("john@example.com").build();

        when(studentService.getStudentByEmail("john@example.com")).thenReturn(studentDO);
        when(studentMapper.mapDoToDto(studentDO)).thenReturn(studentDTO);

        ResponseEntity<StudentDTO> result = studentController.getStudentProfile(authentication);

        assertEquals(studentDTO, result.getBody());
        verify(studentService).getStudentByEmail("john@example.com");
        verify(studentMapper).mapDoToDto(studentDO);
        verifyNoMoreInteractions(studentService, studentMapper);

    }

    @Test
    void testAddStudent() {
        final StudentDTO studentDTO = StudentDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("123456789")
                .address("Address 1")
                .departmentId(1)
                .collegeYear(2)
                .build();
        final StudentDTO createdStudent = StudentDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("123456789")
                .address("Address 1")
                .departmentId(1)
                .collegeYear(2)
                .build();

        when(studentService.addStudent(studentDTO)).thenReturn(createdStudent);

        final StudentDTO result = studentController.addStudent(studentDTO);

        assertEquals(createdStudent, result);
        verify(studentService).addStudent(studentDTO);
        verifyNoMoreInteractions(studentService);
    }

    @Test
    void testDeleteStudent() {
        final long id = 5L;

        studentController.deleteStudent(id);

        verify(studentService).deleteStudent(id);
        verifyNoMoreInteractions(studentService);
    }

    @Test
    void testUpdateStudent() {
        final long id = 10L;
        final StudentDTO studentDTO = StudentDTO.builder()
                .firstName("Updated")
                .lastName("Name")
                .email("updated@example.com")
                .phone("987654321")
                .address("Updated Address")
                .departmentId(2)
                .collegeYear(3)
                .build();
        final StudentDTO updatedStudent = StudentDTO.builder()
                .id(10L)
                .firstName("Updated")
                .lastName("Name")
                .email("updated@example.com")
                .phone("987654321")
                .address("Updated Address")
                .departmentId(2)
                .collegeYear(3)
                .build();

        when(studentService.updateStudent(id, studentDTO)).thenReturn(updatedStudent);

        final StudentDTO result = studentController.updateStudent(id, studentDTO);

        assertEquals(updatedStudent, result);
        verify(studentService).updateStudent(id, studentDTO);
        verifyNoMoreInteractions(studentService);
    }

    @Test
    void testGetStudentById() {
        final long id = 7L;
        final StudentDTO student = StudentDTO.builder()
                .id(7L)
                .firstName("Alice")
                .lastName("Smith")
                .email("alice@example.com")
                .phone("555555555")
                .address("Address 2")
                .departmentId(1)
                .collegeYear(1)
                .build();

        when(studentService.getStudentById(id)).thenReturn(student);

        final StudentDTO result = studentController.getStudentById(id);

        assertEquals(student, result);
        verify(studentService).getStudentById(id);
        verifyNoMoreInteractions(studentService);
    }

    @Test
    void testGetStudents() {
        final List<StudentDTO> students = List.of(
                StudentDTO.builder().id(1L).firstName("A").lastName("A").email("a@example.com").phone("111111111").address("Addr A").departmentId(1).collegeYear(1).build(),
                StudentDTO.builder().id(2L).firstName("B").lastName("B").email("b@example.com").phone("222222222").address("Addr B").departmentId(2).collegeYear(2).build()
        );

        when(studentService.getAllStudents()).thenReturn(students);

        final List<StudentDTO> results = studentController.getStudents();

        assertEquals(students, results);
        verify(studentService).getAllStudents();
        verifyNoMoreInteractions(studentService);
    }
}