package ro.scoalainformala.studentmgmt.grades;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import ro.scoalainformala.studentmgmt.courses.CourseDO;
import ro.scoalainformala.studentmgmt.student.StudentDO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GradesServiceImplUnitTest {

    @Mock
    private GradesRepository gradesRepository;

    @Spy
    @InjectMocks
    private GradesServiceImpl gradesServiceImpl;

    @Mock
    private GradesMapper gradesMapper;

    @BeforeAll
    void beforeAll() {
        openMocks(this);
    }

    @Test
    void getAllGradesByStudentId() {
        final List<GradesDTO> gradesDTOS = List.of(
                GradesDTO.builder().grade(9).studentId(1L).courseId(1L).build(),
                GradesDTO.builder().grade(8).studentId(1L).courseId(2L).build()
        );



        final List<GradesDO> gradesDOS = List.of(
                GradesDO.builder().grade(9f).student(StudentDO.builder().id(1L).build()).course(CourseDO.builder().id(1L).build()).build(),
                GradesDO.builder().grade(8f).student(StudentDO.builder().id(1L).build()).course(CourseDO.builder().id(2L).build()).build()
        );

        when(gradesRepository.findGradesByStudentId(1L)).thenReturn(gradesDOS);
        when(gradesMapper.toGradesDTO(gradesDOS.get(0))).thenReturn(gradesDTOS.get(0));
        when(gradesMapper.toGradesDTO(gradesDOS.get(1))).thenReturn(gradesDTOS.get(1));

        final List<GradesDTO> allGrades = gradesServiceImpl.getAllGradesByStudentId(1L);

        assertEquals(gradesDTOS, allGrades);
        verify(gradesRepository).findGradesByStudentId(1L);
        verifyNoMoreInteractions(gradesRepository);

    }
}