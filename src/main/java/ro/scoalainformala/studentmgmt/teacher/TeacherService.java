package ro.scoalainformala.studentmgmt.teacher;

import java.util.List;

public interface TeacherService {

    List<TeacherDTO> getAllTeachers();
    TeacherDTO getTeacherById(Long id);
    TeacherDTO createTeacher(TeacherDTO teacher);
    TeacherDTO updateTeacher(Long id, TeacherDTO teacher);
    void deleteTeacher(Long id);
}
