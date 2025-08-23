package ro.scoalainformala.studentmgmt.courses;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapperImpl implements CourseMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public CourseMapperImpl(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseDTO mapDoToDto(final CourseDO courseDO) {
        return modelMapper.map(courseDO, CourseDTO.class);
    }

    @Override
    public CourseDO mapDtoToDo(final CourseDTO courseDTO) {
        return modelMapper.map(courseDTO, CourseDO.class);
    }

    @Override
    public List<CourseDTO> mapDoListToDto(final List<CourseDO> courseDOList) {
        return courseDOList.stream().map(this::mapDoToDto).collect(Collectors.toList());
    }

    @Override
    public List<CourseDO> mapDtoListToDo(final List<CourseDTO> courseDTOList) {
        return courseDTOList.stream().map(this::mapDtoToDo).collect(Collectors.toList());
    }
}
