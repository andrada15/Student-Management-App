package ro.scoalainformala.studentmgmt.grades;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GradesMapperImpl implements GradesMapper {

    private final ModelMapper modelMapper;

    public GradesMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public GradesDTO toGradesDTO(GradesDO gradesDO) {
        return modelMapper.map(gradesDO, GradesDTO.class);
    }

    @Override
    public GradesDO toGradesDO(GradesDTO gradesDTO) {
        return modelMapper.map(gradesDTO, GradesDO.class);
    }

    @Override
    public List<GradesDTO> toGradesDTOList(List<GradesDO> gradesDOList) {
        return gradesDOList.stream().map(this::toGradesDTO).collect(Collectors.toList());
    }

    @Override
    public List<GradesDO> toGradesDOList(List<GradesDTO> gradesDTOList) {
        return gradesDTOList.stream().map(this::toGradesDO).collect(Collectors.toList());
    }
}
