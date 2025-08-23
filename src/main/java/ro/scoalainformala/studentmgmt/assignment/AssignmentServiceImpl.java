package ro.scoalainformala.studentmgmt.assignment;

import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ro.scoalainformala.studentmgmt.courses.CourseDO;
import ro.scoalainformala.studentmgmt.courses.CourseRepository;
import ro.scoalainformala.studentmgmt.exceptions.FileNotFoundException;
import ro.scoalainformala.studentmgmt.exceptions.FileStorageException;
import ro.scoalainformala.studentmgmt.files.FileStorageService;
import ro.scoalainformala.studentmgmt.quartzconfig.JobRequest;
import ro.scoalainformala.studentmgmt.quartzconfig.JobRequestRepository;
import ro.scoalainformala.studentmgmt.quartzconfig.QuartzHandler;
import ro.scoalainformala.studentmgmt.student.StudentDO;
import ro.scoalainformala.studentmgmt.student.StudentRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@RequiredArgsConstructor
@Service
public class AssignmentServiceImpl implements AssignmentService{

    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;
    private final FileStorageService fileStorageService;
    private final QuartzHandler quartzHandler;
    private final StudentRepository studentRepository;
    private final JobRequestRepository jobRequestRepository;
    private final CourseRepository courseRepository;


    @Override
    @Transactional
    public AssignmentDTO createAssignment(AssignmentDTO assignmentDTO, MultipartFile file) {
        try {
            String storedFileName = fileStorageService.storeFile(file);
            String originalFileName = file.getOriginalFilename();

            AssignmentDO assignmentDO = assignmentMapper.toAssignmentDO(assignmentDTO);

            CourseDO courseDO = courseRepository.findById(assignmentDTO.getCourseId())
                    .orElseThrow(() -> new EntityNotFoundException("Course not found"));

            assignmentDO.setCourse(courseDO);

            assignmentDO.setStoredFileName(storedFileName);
            assignmentDO.setOriginalFileName(originalFileName);

            List<StudentDO> students = studentRepository.findAllById(assignmentDTO.getStudentId());
            assignmentDO.setStudents(new HashSet<>(students));

            AssignmentDO savedAssignmentDO = assignmentRepository.save(assignmentDO);

            savedAssignmentDO.scheduleReminders();
            for (JobRequest job: savedAssignmentDO.getScheduledJobs()) {
                jobRequestRepository.save(job);
                quartzHandler.scheduleAssignmentReminder(job);
            }
            return assignmentMapper.toAssignmentDTO(savedAssignmentDO);
        } catch (IOException e) {
            throw new FileStorageException("Could not store file", e);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Resource downloadAssignment(Long assignmentId) {
        AssignmentDO assignmentDO = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment with id " + assignmentId + " not found"));

        try {
            return fileStorageService.loadFile(assignmentDO.getStoredFileName());
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("File not found" + assignmentDO.getStoredFileName());
        }
    }

    @Override
    public AssignmentDTO getAssignmentById(Long assignmentId) {
        return assignmentRepository.findById(assignmentId)
                .map(assignmentDO -> {
                    AssignmentDTO assignmentDTO = assignmentMapper.toAssignmentDTO(assignmentDO);

                    if (assignmentDO.getCourse() != null) {
                        assignmentDTO.setCourseId(assignmentDO.getCourse().getId());
                        assignmentDTO.setCourseName(assignmentDO.getCourse().getName());
                    }
                    return assignmentDTO;
                })
                .orElseThrow(() -> new EntityNotFoundException("Assignment with id " + assignmentId + " not found"));
    }


    @Override
    public List<AssignmentDTO> getAllAssignmentsByCourseId(Long courseId) {
        List<AssignmentDO> assignments = assignmentRepository.findByCourseId(courseId);

        return assignments.stream()
                .map(assignment -> {
                    AssignmentDTO assignmentDTO = assignmentMapper.toAssignmentDTO(assignment);

                    if (assignment.getCourse() != null) {
                        assignmentDTO.setCourseName(assignment.getCourse().getName());
                    }
                    return assignmentDTO;
                } ).collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDTO> getAllAssignments() {
        List<AssignmentDO> list = assignmentRepository.findAll();
        return assignmentMapper.toAssignmentDTOList(list);
    }
}
