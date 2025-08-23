package ro.scoalainformala.studentmgmt.grades;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GradesControllerUnitTest {

    @Mock
    private GradesService gradesService;

    @InjectMocks
    private GradesController gradesController;

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

        when(gradesService.getAllGradesByStudentId(1L)).thenReturn(gradesDTOS);

        final List<GradesDTO> allGrades = gradesController.getAllGradesByStudentId(1L);

        assertThat(allGrades).isNotNull();
        assertThat(allGrades.size()).isEqualTo(gradesDTOS.size());
       verify(gradesService).getAllGradesByStudentId(1L);
       verifyNoMoreInteractions(gradesService);

    }
}