package ro.scoalainformala.studentmgmt.teacher;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ro.scoalainformala.studentmgmt.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeacherServiceImplUnitTest {

    @Mock
     private TeacherRepository teacherRepository;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @BeforeAll
    void beforeAll() {
        openMocks(this);
    }

    @Test
    void testGetAllTeachers() {
        final List<TeacherDO> teacherDOs = List.of(
                TeacherDO.builder().id(1L).firstName("John").lastName("Doe").email("john.doe@school.edu").build(),
                TeacherDO.builder().id(2L).firstName("Jane").lastName("Smith").email("jane.smith@school.edu").build()
        );
        final List<TeacherDTO> teacherDTOs = List.of(
                TeacherDTO.builder().id(1L).firstName("John").lastName("Doe").email("john.doe@school.edu").build(),
                TeacherDTO.builder().id(2L).firstName("Jane").lastName("Smith").email("jane.smith@school.edu").build()
        );
        when(teacherRepository.findAll()).thenReturn(teacherDOs);
        when(teacherMapper.mapListDoToDTO(teacherDOs)).thenReturn(teacherDTOs);

        final List<TeacherDTO> result = teacherService.getAllTeachers();

        assertEquals(teacherDTOs, result);
        verify(teacherRepository).findAll();
        verify(teacherMapper).mapListDoToDTO(teacherDOs);
        verifyNoMoreInteractions(teacherRepository, teacherMapper);

    }

    @Test
    void getTeacherById() {
        final long teacherId = 1L;
        final TeacherDO teacherDO = TeacherDO.builder()
                .id(teacherId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        final TeacherDTO teacherDTO = TeacherDTO.builder()
                .id(teacherId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacherDO));
        when(teacherMapper.mapDoToDTO(teacherDO)).thenReturn(teacherDTO);

        final TeacherDTO result = teacherService.getTeacherById(teacherId);

        assertEquals(teacherDTO, result);
        verify(teacherRepository).findById(teacherId);
        verify(teacherMapper).mapDoToDTO(teacherDO);
        verifyNoMoreInteractions(teacherRepository, teacherMapper);
    }

    @Test
    void testGetTeacherByIdNotFound() {
        final long teacherId = 1L;
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        final String message = assertThrows(NotFoundException.class,
                () -> teacherService.getTeacherById(teacherId)).getMessage();
        assertEquals("Teacher wit id " + teacherId + "not found", message);
        verify(teacherRepository).findById(teacherId);
        verifyNoMoreInteractions(teacherRepository);
        verifyNoMoreInteractions(teacherMapper);
    }

    @Test
    void testCreateTeacher() {
        final TeacherDTO teacherDTO = TeacherDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        final TeacherDO teacherDO = TeacherDO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        final TeacherDO savedTeacherDO = TeacherDO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        final TeacherDTO savedTeacherDTO = TeacherDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();

        when(teacherMapper.mapDtoToDO(teacherDTO)).thenReturn(teacherDO);
        when(teacherRepository.save(teacherDO)).thenReturn(savedTeacherDO);
        when(teacherMapper.mapDoToDTO(savedTeacherDO)).thenReturn(savedTeacherDTO);

        final TeacherDTO result = teacherService.createTeacher(teacherDTO);

        assertEquals(savedTeacherDTO, result);
        verify(teacherMapper).mapDtoToDO(teacherDTO);
        verify(teacherRepository).save(teacherDO);
        verify(teacherMapper).mapDoToDTO(savedTeacherDO);
        verifyNoMoreInteractions(teacherRepository, teacherMapper);

    }

    @Test
    void updateTeacher() {
        final long teacherId = 1L;
        final TeacherDTO teacherDTO = TeacherDTO.builder()
                .id(teacherId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        final TeacherDO existingTeacherDO = TeacherDO.builder()
                .id(teacherId)
                .firstName("Old")
                .lastName("Name")
                .email("old.email@school.edu")
                .build();
        final TeacherDO updatedTeacherDO = TeacherDO.builder()
                .id(teacherId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        final TeacherDTO updatedTeacherDTO = TeacherDTO.builder()
                .id(teacherId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(existingTeacherDO));
        when(teacherRepository.save(existingTeacherDO)).thenReturn(updatedTeacherDO);
        when(teacherMapper.mapDoToDTO(updatedTeacherDO)).thenReturn(updatedTeacherDTO);

        final TeacherDTO result = teacherService.updateTeacher(teacherId, teacherDTO);

        assertEquals(updatedTeacherDTO, result);
        verify(teacherRepository).findById(teacherId);
        verify(teacherRepository).save(existingTeacherDO);
        verify(teacherMapper).mapDoToDTO(updatedTeacherDO);
        verifyNoMoreInteractions(teacherRepository, teacherMapper);
    }

    @Test
    void deleteTeacher() {
        final long teacherId = 1L;
        final TeacherDO teacherDO = TeacherDO.builder()
                .id(teacherId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        when(teacherRepository.getById(teacherId)).thenReturn(teacherDO);

        teacherService.deleteTeacher(teacherId);

        verify(teacherRepository).getById(teacherId);
        verify(teacherRepository).delete(teacherDO);
        verifyNoMoreInteractions(teacherRepository);
        verifyNoInteractions(teacherMapper);
    }
}