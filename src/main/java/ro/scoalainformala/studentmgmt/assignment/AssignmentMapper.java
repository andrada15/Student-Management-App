package ro.scoalainformala.studentmgmt.assignment;

import java.util.List;

public interface AssignmentMapper {

    AssignmentDO toAssignmentDO(AssignmentDTO assignmentDTO);
    AssignmentDTO toAssignmentDTO(AssignmentDO assignmentDO);
    List<AssignmentDO> toAssignmentDOList(List<AssignmentDTO> assignmentDTOList);
    List<AssignmentDTO> toAssignmentDTOList(List<AssignmentDO> assignmentDOList);
}
