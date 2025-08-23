package ro.scoalainformala.studentmgmt.courses;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseDO, Long> {

    CourseDO findByName(final String name);
}
