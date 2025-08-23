package ro.scoalainformala.studentmgmt.student;

import java.util.List;

public interface StudentService {

    List<StudentDTO> getAllStudents();

    StudentDTO getStudentById(final Long id);

    StudentDTO addStudent(final StudentDTO student);

    StudentDTO updateStudent(final Long id, final StudentDTO student);

    StudentDO getStudentByEmail(final String email);

    void deleteStudent(final Long id);
}
