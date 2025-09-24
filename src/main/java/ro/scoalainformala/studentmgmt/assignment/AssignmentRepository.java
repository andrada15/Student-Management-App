package ro.scoalainformala.studentmgmt.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<AssignmentDO, Long> {

    List<AssignmentDO> findByCourseId(@Param("courseId") Long courseId);

}
