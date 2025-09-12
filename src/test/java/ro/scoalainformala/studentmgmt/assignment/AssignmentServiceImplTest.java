package ro.scoalainformala.studentmgmt.assignment;

import jakarta.persistence.ManyToOne;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.quartz.SchedulerException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ro.scoalainformala.studentmgmt.files.FileStorageService;
import ro.scoalainformala.studentmgmt.quartzconfig.JobRequest;
import ro.scoalainformala.studentmgmt.quartzconfig.JobRequestRepository;
import ro.scoalainformala.studentmgmt.quartzconfig.QuartzHandler;
import ro.scoalainformala.studentmgmt.student.StudentDO;
import ro.scoalainformala.studentmgmt.student.StudentRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AssignmentServiceImplTest {

    @Mock
    private AssignmentRepository assignmentRepository;
    @Mock
    private AssignmentMapper assignmentMapper;

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private QuartzHandler quartzHandler;

    @Mock
    private JobRequestRepository jobRequestRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private MultipartFile file;

    @Mock
    private Resource resource;

    @InjectMocks
    private AssignmentServiceImpl assignmentService;

    @Test
    void testCreateAssignment() throws IOException, SchedulerException {
        AssignmentDTO assignmentDTO = AssignmentDTO.builder()
                .id(1L)
                .studentId(Collections.singletonList(1L))
                .build();

        AssignmentDO assignmentDO = AssignmentDO.builder()
                .id(1L)
                .students(new HashSet<>())
                .build();

        when(file.getOriginalFilename()).thenReturn("test.pdf");
        when(fileStorageService.storeFile(file)).thenReturn("stored_test.pdf");
        when(assignmentMapper.toAssignmentDO(assignmentDTO)).thenReturn(assignmentDO);
        when(studentRepository.findAllById(any())).thenReturn(List.of(new StudentDO()));
        when(assignmentRepository.save(any())).thenAnswer(invocation -> {
            AssignmentDO saved = invocation.getArgument(0);
            saved.setScheduledJobs(Set.of(new JobRequest()));

            return saved;
        });
        when(assignmentMapper.toAssignmentDTO(any())).thenReturn(assignmentDTO);

        AssignmentDTO result = assignmentService.createAssignment(assignmentDTO, file);

        assertNotNull(result);
        verify(fileStorageService).storeFile(file);
        verify(assignmentRepository).save(any(AssignmentDO.class));
        verify(jobRequestRepository, atLeastOnce()).save(any(JobRequest.class));
        verify(quartzHandler, atLeastOnce()).scheduleAssignmentReminder(any(JobRequest.class));

    }

    @Test
    void testDownloadAssignment() throws MalformedURLException {
        AssignmentDTO assignmentDTO = AssignmentDTO.builder()
                .id(1L)
                .studentId(Collections.singletonList(1L))
                .build();

        AssignmentDO assignmentDO = AssignmentDO.builder()
                .id(1L)
                .students(new HashSet<>())
                .build();

        assignmentDO.setStoredFileName("stored_test.pdf");
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignmentDO));
        when(fileStorageService.loadFile("stored_test.pdf")).thenReturn(resource);

        Resource result = assignmentService.downloadAssignment(1L);

        assertNotNull(result);
        assertEquals(resource, result);
        verify(fileStorageService).loadFile("stored_test.pdf");

    }

    @Test
    void testGetAssignmentById() {
        final long id = 1L;
        final AssignmentDO assignmentDO = AssignmentDO.builder()
                .id(id)
                .name("Quantum Mechanics")
                .description("exercises")
                .startDate(LocalDateTime.of(2025, 07, 15, 10, 0))
                .expirationDate(LocalDateTime.of(2025, 07, 29, 23,59))
                .build();

        final AssignmentDTO assignmentDTO = AssignmentDTO.builder()
                .id(id)
                .name("Quantum Mechanics")
                .description("exercises")
                .startDate(LocalDateTime.of(2025, 7, 15, 10, 0))
                .expirationDate(LocalDateTime.of(2025, 7, 29, 23,59))
                .build();



       when(assignmentRepository.findById(id)).thenReturn(Optional.of(assignmentDO));
       when(assignmentMapper.toAssignmentDTO(assignmentDO)).thenReturn(assignmentDTO);

       final AssignmentDTO result = assignmentService.getAssignmentById(id);

       assertEquals(assignmentDTO, result);
       verify(assignmentRepository).findById(id);
       verify(assignmentMapper).toAssignmentDTO(assignmentDO);
       verifyNoMoreInteractions(assignmentRepository, assignmentMapper);
    }

    @Test
    void getAllAssignmentsByCourseId() {
        final long id = 1L;

        final AssignmentDO assignmentDO = AssignmentDO.builder()
                .id(1L)
                .name("Quantum Mechanics")
                .description("exercises")
                .startDate(LocalDateTime.of(2025, 07, 15, 10, 0))
                .expirationDate(LocalDateTime.of(2025, 07, 29, 23,59))
                .build();

        final AssignmentDTO assignmentDTO = AssignmentDTO.builder()
                .id(1L)
                .courseId(id)
                .name("Quantum Mechanics")
                .description("exercises")
                .startDate(LocalDateTime.of(2025, 07, 15, 10, 0))
                .expirationDate(LocalDateTime.of(2025, 07, 29, 23,59))
                .build();

        when (assignmentRepository.findByCourseId(id)).thenReturn(List.of(assignmentDO));
        when (assignmentMapper.toAssignmentDTO(assignmentDO)).thenReturn(assignmentDTO);

        final List<AssignmentDTO> result = assignmentService.getAllAssignmentsByCourseId(id);

        assertEquals(result, assignmentDTO);
        verify(assignmentService).getAllAssignmentsByCourseId(id);
        verifyNoMoreInteractions(assignmentService);

    }

}