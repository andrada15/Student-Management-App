package ro.scoalainformala.studentmgmt.assignment;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssignmentMapperImpl implements AssignmentMapper {

    private final ModelMapper modelMapper;

    public AssignmentMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AssignmentDO toAssignmentDO(AssignmentDTO assignmentDTO) {
        return modelMapper.map(assignmentDTO, AssignmentDO.class);
    }

    @Override
    public AssignmentDTO toAssignmentDTO(AssignmentDO assignmentDO) {
        return modelMapper.map(assignmentDO, AssignmentDTO.class);
    }

    @Override
    public List<AssignmentDO> toAssignmentDOList(List<AssignmentDTO> assignmentDTOList) {
        return assignmentDTOList.stream().map(this::toAssignmentDO).collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDTO> toAssignmentDTOList(List<AssignmentDO> assignmentDOList) {
        return assignmentDOList.stream().map(this::toAssignmentDTO).collect(Collectors.toList());
    }
}
