package ro.scoalainformala.studentmgmt.grades;

import java.util.List;

public interface GradesService {

    List<GradesDTO> getAllGradesByStudentId(final Long studentId);
}
