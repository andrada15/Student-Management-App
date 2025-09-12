package ro.scoalainformala.studentmgmt.assignment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AssignmentControllerUnitTest {

    @Mock
    private AssignmentService assignmentService;

    @InjectMocks
    private AssignmentController assignmentController;

    private ObjectMapper objectMapper;

    @BeforeAll
    public void beforeAll() {
        openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateAssignment() throws JsonProcessingException {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setId(1L);
        assignmentDTO.setName("Test Assignment");

        String assignmentJson = objectMapper.writeValueAsString(assignmentDTO);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "Sample content".getBytes(StandardCharsets.UTF_8)
        );

        when(assignmentService.createAssignment(any(AssignmentDTO.class), any(MockMultipartFile.class)))
                .thenReturn(assignmentDTO);

        ResponseEntity<AssignmentDTO> response = assignmentController.createAssignment(assignmentJson, file);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Assignment", response.getBody().getName());
    }

    @Test
    public void testDownloadAssignment() {

        Long assignmentId = 1L;

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setId(assignmentId);
        assignmentDTO.setOriginalFileName("assignment1.pdf");

        Resource resource = new ByteArrayResource("File content".getBytes(StandardCharsets.UTF_8));

        when(assignmentService.downloadAssignment(assignmentId)).thenReturn(resource);
        when(assignmentService.getAssignmentById(assignmentId)).thenReturn(assignmentDTO);

        ResponseEntity<Resource> response = assignmentController.downloadAssignment(assignmentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("File content", new String(((ByteArrayResource) response.getBody()).getByteArray(), StandardCharsets.UTF_8));

        String contentDisposition = response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION);
        assertNotNull(contentDisposition);
        assertTrue(contentDisposition.contains("assignment1.pdf"));

    }

    @Test
    public void testGetAssignmentByCourse() {
        final long id = 1L;
        final AssignmentDTO mockedAssignment = AssignmentDTO.builder()
                .courseId(id)
                .name("Quantum Mechanics")
                .description("exercises")
                .startDate(LocalDateTime.of(2025, 07, 15, 10, 0))
                .expirationDate(LocalDateTime.of(2025, 07, 29, 23,59))
                .build();

        when (assignmentService.getAllAssignmentsByCourseId(id)).thenReturn((List<AssignmentDTO>) mockedAssignment);

        final ResponseEntity<List<AssignmentDTO>> result = assignmentController.getAssignmentsByCourse(id);

        assertEquals(result, mockedAssignment);
        verify(assignmentService).getAllAssignmentsByCourseId(id);
        verifyNoMoreInteractions(assignmentService);
    }

    @Test
    public void testGetAssignmentById() {
        final long id = 1L;
        final AssignmentDTO mockedAssignment = AssignmentDTO.builder()
                .id(id)
                .courseId(2L)
                .name("Quantum Mechanics")
                .description("exercises")
                .startDate(LocalDateTime.of(2025, 07, 15, 10, 0))
                .expirationDate(LocalDateTime.of(2025, 07, 29, 23,59))
                .build();

        when (assignmentService.getAssignmentById(id)).thenReturn(mockedAssignment);

        final ResponseEntity<AssignmentDTO> result = assignmentController.getAssignmentById(id);

        assertEquals(result, mockedAssignment);
        verify(assignmentService).getAssignmentById(id);
        verifyNoMoreInteractions(assignmentService);

    }



}