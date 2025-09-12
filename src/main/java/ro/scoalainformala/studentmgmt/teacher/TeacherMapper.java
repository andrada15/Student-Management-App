package ro.scoalainformala.studentmgmt.teacher;

import java.util.List;

public interface TeacherMapper {

    TeacherDTO mapDoToDTO(TeacherDO teacherDO);
    TeacherDO mapDtoToDO(TeacherDTO teacherDTO);
    List<TeacherDTO> mapListDoToDTO(List<TeacherDO> teacherDOList);
    List<TeacherDO> mapListDtoToDO(List<TeacherDTO> teacherDTOList);
}
