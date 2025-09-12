package ro.scoalainformala.studentmgmt.grades;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import ro.scoalainformala.studentmgmt.student.StudentDO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GradesMapperImplUnitTest {

    @Mock
    private ModelMapper modelMapper;

    @Spy
    @InjectMocks
    private GradesMapperImpl gradesMapper;

    @BeforeAll
    void beforeAll() {
        openMocks(this);
    }

    @AfterEach
    void afterEach() {
        reset(modelMapper, gradesMapper);
    }

    @Test
    void testToGradesDTO() {
        final GradesDO gradesDO = GradesDO.builder()
                .id(1L)
                .student(StudentDO.builder().id(2L).build())
                .grade(10f)
                .build();
        final GradesDTO mockedGradesDTO = GradesDTO.builder()
                .id(1L)
                .studentId(2L)
                .grade(10)
                .build();
        when(modelMapper.map(gradesDO, GradesDTO.class)).thenReturn(mockedGradesDTO);

        GradesDTO result = gradesMapper.toGradesDTO(gradesDO);

        assertEquals(mockedGradesDTO, result);
        verify(modelMapper).map(gradesDO, GradesDTO.class);
        verifyNoMoreInteractions(modelMapper);
    }

    @Test
    void testToGradesDO() {
        final GradesDO gradesDO = GradesDO.builder()
                .id(1L)
                .student(StudentDO.builder().id(2L).build())
                .grade(10f)
                .build();
        final GradesDTO gradesDTO = GradesDTO.builder()
                .id(1L)
                .studentId(2L)
                .grade(10)
                .build();

        when(modelMapper.map(gradesDTO, GradesDO.class)).thenReturn(gradesDO);

        GradesDO result = gradesMapper.toGradesDO(gradesDTO);

        assertEquals(gradesDO, result);
        verify(modelMapper).map(gradesDTO, GradesDO.class);
        verifyNoMoreInteractions(modelMapper);

    }

    @Test
    void testToGradesDTOList() {
        final GradesDO gradesDO1 = GradesDO.builder()
                .id(1L)
                .student(StudentDO.builder().id(1L).build())
                .grade(10f)
                .build();
        final GradesDO gradesDO2 = GradesDO.builder()
                .id(1L)
                .student(StudentDO.builder().id(2L).build())
                .grade(10f)
                .build();
        final GradesDTO gradesDTO1 = GradesDTO.builder()
                .id(1L)
                .studentId(1L)
                .grade(10)
                .build();
        final GradesDTO gradesDTO2 = GradesDTO.builder()
                .id(1L)
                .studentId(2L)
                .grade(10)
                .build();

        when(modelMapper.map(gradesDTO1, GradesDO.class)).thenReturn(gradesDO1);
        when(modelMapper.map(gradesDTO2, GradesDO.class)).thenReturn(gradesDO2);
        when(gradesMapper.toGradesDO(gradesDTO1)).thenReturn(gradesDO1);
        when(gradesMapper.toGradesDO(gradesDTO2)).thenReturn(gradesDO2);

        List<GradesDO> result = gradesMapper.toGradesDOList(List.of(gradesDTO1, gradesDTO2));

        assertEquals(List.of(gradesDO1, gradesDO2), result);
        verify(modelMapper).map(gradesDTO1, GradesDO.class);
        verify(modelMapper).map(gradesDTO2, GradesDO.class);
        verify(gradesMapper).toGradesDO(gradesDTO1);
    }

    @Test
    void testToGradesDOList() {
        final GradesDO gradesDO1 = GradesDO.builder()
                .id(1L)
                .student(StudentDO.builder().id(1L).build())
                .grade(10f)
                .build();
        final GradesDO gradesDO2 = GradesDO.builder()
                .id(1L)
                .student(StudentDO.builder().id(2L).build())
                .grade(10f)
                .build();
        final GradesDTO gradesDTO1 = GradesDTO.builder()
                .id(1L)
                .studentId(1L)
                .grade(10)
                .build();
        final GradesDTO gradesDTO2 = GradesDTO.builder()
                .id(1L)
                .studentId(2L)
                .grade(10)
                .build();
        when(modelMapper.map(gradesDO1, GradesDTO.class)).thenReturn(gradesDTO1);
        when(modelMapper.map(gradesDO2, GradesDTO.class)).thenReturn(gradesDTO2);
        when(gradesMapper.toGradesDTO(gradesDO1)).thenReturn(gradesDTO1);
        when(gradesMapper.toGradesDTO(gradesDO2)).thenReturn(gradesDTO2);

        List<GradesDTO> result = gradesMapper.toGradesDTOList(List.of(gradesDO1, gradesDO2));

        assertEquals(List.of(gradesDTO1, gradesDTO2), result);
        verify(modelMapper).map(gradesDO1, GradesDTO.class);
        verify(modelMapper).map(gradesDO2, GradesDTO.class);
        verify(gradesMapper).toGradesDTO(gradesDO1);

    }
}