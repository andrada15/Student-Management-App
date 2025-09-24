package ro.scoalainformala.studentmgmt.assignment;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AssignmentController {

    private final AssignmentService assignmentService;


    @Autowired
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AssignmentDTO> createAssignment(
            @RequestPart("assignment") String assignmentJson,
            @RequestPart("file") MultipartFile file
    ) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            AssignmentDTO assignment = mapper.readValue(assignmentJson, AssignmentDTO.class);
            if (assignment.getName() == null || assignment.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body((AssignmentDTO) Map.of("error", "Assignment name is required"));
            }

            if (assignment.getCourseId() == null) {
                return ResponseEntity.badRequest()
                        .body((AssignmentDTO) Map.of("error", "Course ID is required"));
            }

            if (assignment.getExpirationDate() == null) {
                return ResponseEntity.badRequest()
                        .body((AssignmentDTO) Map.of("error", "Expiration date is required"));
            }

            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body((AssignmentDTO) Map.of("error", "Assignment file is required"));
            }
            AssignmentDTO createdAssignment = assignmentService.createAssignment(assignment, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAssignment);
        } catch (JsonProcessingException e) {
            log.error("Error parsing assignment JSON", e);
            return ResponseEntity.badRequest()
                    .body((AssignmentDTO) Map.of("error", "Invalid assignment data: " + e.getMessage()));
        } catch (Exception e) {
            log.error("Error creating assignment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body((AssignmentDTO) Map.of("error", "Failed to create assignment: " + e.getMessage()));
        }


    }

    @GetMapping("/{id}/file")
    @PreAuthorize("hasAnyRole('TEACHER', STUDENT)")
    public ResponseEntity<Resource> downloadAssignment(@PathVariable Long id) {
        Resource file = assignmentService.downloadAssignment(id);
        AssignmentDTO assignmentDTO = assignmentService.getAssignmentById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + assignmentDTO.getOriginalFileName() + "\"").body(file);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsByCourse(@PathVariable Long courseId) {

        List<AssignmentDTO> assignments = assignmentService.getAllAssignmentsByCourseId(courseId);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDTO> getAssignmentById(@PathVariable Long id) {
        AssignmentDTO assignment = assignmentService.getAssignmentById(id);
        return ResponseEntity.ok(assignment);
    }

    @GetMapping
    public List<AssignmentDTO> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }
}



