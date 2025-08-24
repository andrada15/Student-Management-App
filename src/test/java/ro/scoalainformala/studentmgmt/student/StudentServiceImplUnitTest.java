package ro.scoalainformala.studentmgmt.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.scoalainformala.studentmgmt.exceptions.NotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceImplUnitTest {
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private StudentValidator studentValidator;

    @InjectMocks
    private StudentServiceImpl studentService;

    private StudentDO studentDO;
    private StudentDTO studentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        studentDO = new StudentDO();
        studentDO.setId(1L);
        studentDO.setFirstName("John");
        studentDO.setLastName("Doe");
        studentDO.setEmail("john@example.com");

        studentDTO = new StudentDTO();
        studentDTO.setId(1L);
        studentDTO.setFirstName("John");
        studentDTO.setLastName("Doe");
        studentDTO.setEmail("john@example.com");
    }

    @Test
    void testGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(studentDO));
        when(studentMapper.mapDoToDtoList(Arrays.asList(studentDO))).thenReturn(Arrays.asList(studentDTO));

        List<StudentDTO> result = studentService.getAllStudents();

        assertThat(result.get(0).getFirstName()).isEqualTo("John");
        verify(studentRepository, times(1)).findAll();
        verify(studentMapper, times(1)).mapDoToDtoList(anyList());
    }

    @Test
    void testGetStudentById_Found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(studentDO));
        when(studentMapper.mapDoToDto(studentDO)).thenReturn(studentDTO);

        StudentDTO result = studentService.getStudentById(1L);

        assertThat(result.getEmail()).isEqualTo("john@example.com");
        verify(studentRepository).findById(1L);
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getStudentById(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Student with id1not found");
    }

    @Test
    void testAddStudent() {
        when(studentMapper.mapDtoToDo(studentDTO)).thenReturn(studentDO);
        when(studentRepository.save(studentDO)).thenReturn(studentDO);
        when(studentMapper.mapDoToDto(studentDO)).thenReturn(studentDTO);

        StudentDTO result = studentService.addStudent(studentDTO);

        assertThat(result.getFirstName()).isEqualTo("John");
        verify(studentValidator).validateStudent(studentDTO);
        verify(studentRepository).save(studentDO);
    }

    @Test
    void testUpdateStudent_Found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(studentDO));
        when(studentRepository.save(studentDO)).thenReturn(studentDO);
        when(studentMapper.mapDoToDto(studentDO)).thenReturn(studentDTO);

        StudentDTO result = studentService.updateStudent(1L, studentDTO);

        assertThat(result.getFirstName()).isEqualTo("John");
        verify(studentValidator).validateStudent(studentDTO);
        verify(studentRepository).save(studentDO);
    }

    @Test
    void testUpdateStudent_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.updateStudent(1L, studentDTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Student not found with id: 1");
    }

    @Test
    void testGetStudentByEmail_Found() {
        when(studentRepository.findByEmail("john@example.com")).thenReturn(Optional.of(studentDO));

        StudentDO result = studentService.getStudentByEmail("john@example.com");

        assertThat(result.getFirstName()).isEqualTo("John");
        verify(studentRepository).findByEmail("john@example.com");
    }

    @Test
    void testGetStudentByEmail_NotFound() {
        when(studentRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getStudentByEmail("john@example.com"))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("The student with email: john@example.com not found");
    }

    @Test
    void testDeleteStudent() {
        when(studentRepository.getById(1L)).thenReturn(studentDO);

        studentService.deleteStudent(1L);

        verify(studentRepository).delete(studentDO);
    }
}