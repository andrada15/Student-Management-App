package ro.scoalainformala.studentmgmt.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentMapperImplUnitTest {

    @Mock
    private ModelMapper modelMapper;

    @Spy
    @InjectMocks
    private StudentMapperImpl studentMapper;

    @BeforeAll
    void beforeAll() {
        openMocks(this);
    }

    @AfterEach
    void afterEach() {
        reset(modelMapper, studentMapper);
    }

    @Test
    void testMapDoToDto() {
        final StudentDO studentDO = StudentDO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("123456789")
                .address("Address 1")
                .departmentId(1)
                .collegeYear(2)
                .build();
        final StudentDTO mockedStudentDTO = StudentDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("123456789")
                .address("Address 1")
                .departmentId(1)
                .collegeYear(2)
                .build();
        when(modelMapper.map(studentDO, StudentDTO.class)).thenReturn(mockedStudentDTO);

        final StudentDTO result = studentMapper.mapDoToDto(studentDO);

        assertEquals(mockedStudentDTO, result);
        verify(modelMapper).map(studentDO, StudentDTO.class);
        verifyNoMoreInteractions(modelMapper);
    }

    @Test
    void testMapDtoToDo() {
        final StudentDTO studentDTO = StudentDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("123456789")
                .address("Address 1")
                .departmentId(1)
                .collegeYear(2)
                .build();
        final StudentDO mockedStudentDO = StudentDO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("123456789")
                .address("Address 1")
                .departmentId(1)
                .collegeYear(2)
                .build();
        when(modelMapper.map(studentDTO, StudentDO.class)).thenReturn(mockedStudentDO);

        final StudentDO result = studentMapper.mapDtoToDo(studentDTO);

        assertEquals(mockedStudentDO, result);
        verify(modelMapper).map(studentDTO, StudentDO.class);
        verifyNoMoreInteractions(modelMapper);
    }

    @Test
    void testMapDoToDtoList() {
        final StudentDO studentDO1 = StudentDO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("123456789")
                .address("Address 1")
                .departmentId(1)
                .collegeYear(2)
                .build();
        final StudentDO studentDO2 = StudentDO.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("987654321")
                .address("Address 2")
                .departmentId(2)
                .collegeYear(3)
                .build();
        final StudentDTO studentDTO1 = StudentDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("123456789")
                .address("Address 1")
                .departmentId(1)
                .collegeYear(2)
                .build();
        final StudentDTO studentDTO2 = StudentDTO.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("987654321")
                .address("Address 2")
                .departmentId(2)
                .collegeYear(3)
                .build();

        when(modelMapper.map(studentDO1, StudentDTO.class)).thenReturn(studentDTO1);
        when(modelMapper.map(studentDO2, StudentDTO.class)).thenReturn(studentDTO2);
        final List<StudentDO> studentDOs = List.of(studentDO1, studentDO2);
        when(studentMapper.mapDoToDto(studentDO1)).thenReturn(studentDTO1);
        when(studentMapper.mapDoToDto(studentDO2)).thenReturn(studentDTO2);

        final List<StudentDTO> result = studentMapper.mapDoToDtoList(studentDOs);

        assertEquals(List.of(studentDTO1, studentDTO2), result);
        verify(modelMapper).map(studentDO1, StudentDTO.class);
        verify(modelMapper).map(studentDO2, StudentDTO.class);
        verify(studentMapper).mapDoToDto(studentDO1);
    }

    @Test
    void mapDtoToListDoList() {
        final StudentDTO studentDTO1 = StudentDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("123456789")
                .address("Address 1")
                .departmentId(1)
                .collegeYear(2)
                .build();
        final StudentDTO studentDTO2 = StudentDTO.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("987654321")
                .address("Address 2")
                .departmentId(2)
                .collegeYear(3)
                .build();
        final StudentDO studentDO1 = StudentDO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("123456789")
                .address("Address 1")
                .departmentId(1)
                .collegeYear(2)
                .build();
        final StudentDO studentDO2 = StudentDO.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("987654321")
                .address("Address 2")
                .departmentId(2)
                .collegeYear(3)
                .build();

        when(modelMapper.map(studentDTO1, StudentDO.class)).thenReturn(studentDO1);
        when(modelMapper.map(studentDTO2, StudentDO.class)).thenReturn(studentDO2);
        final List<StudentDTO> studentDTOs = List.of(studentDTO1, studentDTO2);
        when(studentMapper.mapDtoToDo(studentDTO1)).thenReturn(studentDO1);
        when(studentMapper.mapDtoToDo(studentDTO2)).thenReturn(studentDO2);

        final List<StudentDO> result = studentMapper.mapDtotoListDoList(studentDTOs);

        assertEquals(List.of(studentDO1, studentDO2), result);
        verify(modelMapper).map(studentDTO1, StudentDO.class);
        verify(modelMapper).map(studentDTO2, StudentDO.class);
        verify(studentMapper).mapDtoToDo(studentDTO1);
    }

}