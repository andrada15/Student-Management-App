package ro.scoalainformala.studentmgmt.student;

import java.util.List;

public interface StudentMapper {

    StudentDTO mapDoToDto(final StudentDO studentDO);

    StudentDO mapDtoToDo(final StudentDTO studentDTO);

    List<StudentDTO> mapDoToDtoList(final List<StudentDO> studentDOList);

    List<StudentDO> mapDtotoListDoList(final List<StudentDTO> studentDTOList);



}
