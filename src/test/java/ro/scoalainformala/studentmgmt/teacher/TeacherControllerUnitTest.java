package ro.scoalainformala.studentmgmt.teacher;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeacherControllerUnitTest {

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController teacherController;

    @BeforeAll
    void beforeAll() {
        openMocks(this);
    }


    @Test
    void testGetTeachers() {
        List<TeacherDTO> mockedTeachers = List.of(
                TeacherDTO.builder().id(1L).firstName("Alice").lastName("Smith").email("alice@example.com").department("Physics").build(),
                TeacherDTO.builder().id(2L).firstName("Bob").lastName("Johnson").email("bob@example.com").department("Chemistry").build()
        );

        when(teacherService.getAllTeachers()).thenReturn(mockedTeachers);
        List<TeacherDTO> results = teacherController.getTeachers();

        assertEquals(mockedTeachers, results);
        verify(teacherService).getAllTeachers();
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    void testGetTeacherById() {
        long id = 42L;
        TeacherDTO mockedTeacher = TeacherDTO.builder()
                .id(id)
                .firstName("Emma")
                .lastName("Brown")
                .email("emma@example.com")
                .department("Biology")
                .build();

        when(teacherService.getTeacherById(id)).thenReturn(mockedTeacher);
        TeacherDTO result = teacherController.getTeacherById(id);

        assertEquals(mockedTeacher, result);
        verify(teacherService).getTeacherById(id);
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    void testCreateTeacher() {
        TeacherDTO teacherDTO = TeacherDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .department("Mathematics")
                .build();

        TeacherDTO createdTeacher = TeacherDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .department("Mathematics")
                .build();

        when(teacherService.createTeacher(teacherDTO)).thenReturn(createdTeacher);
        TeacherDTO result = teacherController.createTeacher(teacherDTO);
        assertEquals(createdTeacher, result);
        verify(teacherService).createTeacher(teacherDTO);
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    void updateTeacher() {
        long id = 10L;
        TeacherDTO updateData = TeacherDTO.builder()
                .firstName("UpdatedName")
                .lastName("UpdatedLast")
                .email("updated@example.com")
                .department("UpdatedDept")
                .build();

        TeacherDTO updatedTeacher = TeacherDTO.builder()
                .id(id)
                .firstName("UpdatedName")
                .lastName("UpdatedLast")
                .email("updated@example.com")
                .department("UpdatedDept")
                .build();

        when(teacherService.updateTeacher(id, updateData)).thenReturn(updatedTeacher);

        TeacherDTO result = teacherController.updateTeacher(id, updateData);

        assertEquals(updatedTeacher, result);
        verify(teacherService).updateTeacher(id, updateData);
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    void deleteTeacher() {
        long id = 5L;

        teacherController.deleteTeacher(id);

        verify(teacherService).deleteTeacher(id);
        verifyNoMoreInteractions(teacherService);
    }
}