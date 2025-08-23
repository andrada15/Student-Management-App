package ro.scoalainformala.studentmgmt.teacher;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeacherMapperImpl implements TeacherMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public TeacherMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public TeacherDTO mapDoToDTO(TeacherDO teacherDO) {
        return modelMapper.map(teacherDO, TeacherDTO.class);
    }

    @Override
    public TeacherDO mapDtoToDO(TeacherDTO teacherDTO) {
        return modelMapper.map(teacherDTO, TeacherDO.class);
    }

    @Override
    public List<TeacherDTO> mapListDoToDTO(List<TeacherDO> teacherDOList) {
        return teacherDOList.stream().map(this::mapDoToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TeacherDO> mapListDtoToDO(List<TeacherDTO> teacherDTOList) {
        return teacherDTOList.stream().map(this::mapDtoToDO).collect(Collectors.toList());
    }
}
