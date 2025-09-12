package ro.scoalainformala.studentmgmt.teacher;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherDO, Long> {
}
