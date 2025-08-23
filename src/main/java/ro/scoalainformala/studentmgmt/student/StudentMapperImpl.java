package ro.scoalainformala.studentmgmt.student;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentMapperImpl implements StudentMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public StudentMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public StudentDTO mapDoToDto(final StudentDO studentDO) {
        return modelMapper.map(studentDO, StudentDTO.class);
    }

    @Override
    public StudentDO mapDtoToDo(final StudentDTO studentDTO) {
        return modelMapper.map(studentDTO, StudentDO.class);
    }

    @Override
    public List<StudentDTO> mapDoToDtoList(List<StudentDO> studentDOList) {
        return studentDOList.stream().map(this::mapDoToDto).collect(Collectors.toList());
    }

    @Override
    public List<StudentDO> mapDtotoListDoList(List<StudentDTO> studentDTOList) {
        return studentDTOList.stream()
                .map(this::mapDtoToDo)
                .collect(Collectors.toList());
    }

}
