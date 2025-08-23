package ro.scoalainformala.studentmgmt.courses;

import java.util.List;

public interface CourseMapper {

    CourseDTO mapDoToDto(final CourseDO courseDO);
    CourseDO mapDtoToDo(final CourseDTO courseDTO);
    List<CourseDTO> mapDoListToDto(final List<CourseDO> courseDOList);
    List<CourseDO> mapDtoListToDo(final List<CourseDTO> courseDTOList);
}
