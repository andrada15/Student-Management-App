package ro.scoalainformala.studentmgmt.assignment;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AssignmentService {

    AssignmentDTO createAssignment(AssignmentDTO assignmentDTO, MultipartFile file);
    Resource downloadAssignment(Long assignmentId);
    AssignmentDTO getAssignmentById(Long assignmentId);
    List<AssignmentDTO> getAllAssignmentsByCourseId(Long courseId);
    List<AssignmentDTO> getAllAssignments();
}
