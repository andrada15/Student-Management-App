package ro.scoalainformala.studentmgmt.grades;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradesRepository extends JpaRepository<GradesDO, Long> {
    List<GradesDO> findGradesByStudentId(final Long studentId);
}
