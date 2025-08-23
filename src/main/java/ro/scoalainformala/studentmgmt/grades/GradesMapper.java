package ro.scoalainformala.studentmgmt.grades;

import java.util.List;

public interface GradesMapper {

    GradesDTO toGradesDTO(GradesDO gradesDO);
    GradesDO toGradesDO(GradesDTO gradesDTO);

    List<GradesDTO> toGradesDTOList(List<GradesDO> gradesDOList);
    List<GradesDO> toGradesDOList(List<GradesDTO> gradesDTOList);
}
