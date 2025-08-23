package ro.scoalainformala.studentmgmt.assignment;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AssignmentMapperImplUnitTest {

    @Mock
    private ModelMapper modelMapper;

    @Spy
    @InjectMocks
    private AssignmentMapperImpl assignmentMapper;

    @BeforeAll
    void beforeAll() {
        openMocks(this);
    }

    @AfterEach
    void beforeEach() {
        reset(modelMapper, assignmentMapper);
    }



    @Test
    void testToAssignmentDO() {
        final AssignmentDO assignmentDO = AssignmentDO.builder()
                .id(1L)
                .name("C programming")
                .description("Make a program")
                .startDate(LocalDateTime.of(2025, 10, 10, 10, 10))
                .expirationDate(LocalDateTime.of(2025, 10, 15, 23, 59))
                .build();

        final AssignmentDTO assignmentDTO = AssignmentDTO.builder()
                .id(1L)
                .name("C programming")
                .description("Make a program")
                .startDate(LocalDateTime.of(2025, 10, 10, 10, 10))
                .expirationDate(LocalDateTime.of(2025, 10, 15, 23, 59))
                .build();

        when(modelMapper.map(assignmentDO, AssignmentDTO.class)).thenReturn(assignmentDTO);

        final AssignmentDTO result = assignmentMapper.toAssignmentDTO(assignmentDO);

        assertEquals(assignmentDTO, result);
        verify(modelMapper).map(assignmentDO, AssignmentDTO.class);
        verifyNoMoreInteractions(modelMapper);
    }

    @Test
    void toAssignmentDTO() {
        final AssignmentDTO assignmentDTO = AssignmentDTO.builder()
                .id(1L)
                .name("C programming")
                .description("Make a program")
                .startDate(LocalDateTime.of(2025, 10, 10, 10, 10))
                .expirationDate(LocalDateTime.of(2025, 10, 15, 23, 59))
                .build();

        final AssignmentDO assignmentDO = AssignmentDO.builder()
                .id(1L)
                .name("C programming")
                .description("Make a program")
                .startDate(LocalDateTime.of(2025, 10, 10, 10, 10))
                .expirationDate(LocalDateTime.of(2025, 10, 15, 23, 59))
                .build();

        when(modelMapper.map(assignmentDTO, AssignmentDO.class)).thenReturn(assignmentDO);

        final AssignmentDO result = assignmentMapper.toAssignmentDO(assignmentDTO);

        assertEquals(assignmentDO, result);
        verify(modelMapper).map(assignmentDTO, AssignmentDO.class);
        verifyNoMoreInteractions(modelMapper);
    }

    @Test
    void toAssignmentDOList() {
        final AssignmentDTO assignmentDTO1 = AssignmentDTO.builder()
                .id(1L)
                .name("Example1")
                .description("Description1")
                .startDate(LocalDateTime.of(2025, 9, 15, 9, 0))
                .expirationDate(LocalDateTime.of(2025, 10, 15, 23, 59))
                .build();

        final AssignmentDTO assignmentDTO2 = AssignmentDTO.builder()
                .id(2L)
                .name("Example2")
                .description("Description2")
                .startDate(LocalDateTime.of(2025, 9, 15, 9, 0))
                .expirationDate(LocalDateTime.of(2025, 10, 15, 23, 59))
                .build();

        final AssignmentDO assignmentDO1 = AssignmentDO.builder()
                .id(1L)
                .name("Example1")
                .description("Description1")
                .startDate(LocalDateTime.of(2025, 9, 15, 9, 0))
                .expirationDate(LocalDateTime.of(2025, 10, 15, 23, 59))
                .build();

        final AssignmentDO assignmentDO2 = AssignmentDO.builder()
                .id(2L)
                .name("Example2")
                .description("Description2")
                .startDate(LocalDateTime.of(2025, 9, 15, 9, 0))
                .expirationDate(LocalDateTime.of(2025, 10, 15, 23, 59))
                .build();

        when(modelMapper.map(assignmentDTO1, AssignmentDO.class)).thenReturn(assignmentDO1);
        when(modelMapper.map(assignmentDTO2, AssignmentDO.class)).thenReturn(assignmentDO2);
        final List<AssignmentDTO> assignmentDTOS = List.of(assignmentDTO1, assignmentDTO2);
        when(assignmentMapper.toAssignmentDO(assignmentDTO1)).thenReturn(assignmentDO1);
        when(assignmentMapper.toAssignmentDO(assignmentDTO2)).thenReturn(assignmentDO2);

        final List<AssignmentDO> result = assignmentMapper.toAssignmentDOList(assignmentDTOS);

        assertEquals(List.of(assignmentDO1, assignmentDO2), result);
        verify(modelMapper).map(assignmentDTO1, AssignmentDO.class);
        verify(modelMapper).map(assignmentDTO2, AssignmentDO.class);
        verify(assignmentMapper).toAssignmentDO(assignmentDTO1);

    }

    @Test
    void toAssignmentDTOList() {
        final AssignmentDTO assignmentDTO1 = AssignmentDTO.builder()
                .id(1L)
                .name("Example1")
                .description("Description1")
                .startDate(LocalDateTime.of(2025, 9, 15, 9, 0))
                .expirationDate(LocalDateTime.of(2025, 10, 15, 23, 59))
                .build();

        final AssignmentDTO assignmentDTO2 = AssignmentDTO.builder()
                .id(2L)
                .name("Example2")
                .description("Description2")
                .startDate(LocalDateTime.of(2025, 9, 15, 9, 0))
                .expirationDate(LocalDateTime.of(2025, 10, 15, 23, 59))
                .build();

        final AssignmentDO assignmentDO1 = AssignmentDO.builder()
                .id(1L)
                .name("Example1")
                .description("Description1")
                .startDate(LocalDateTime.of(2025, 9, 15, 9, 0))
                .expirationDate(LocalDateTime.of(2025, 10, 15, 23, 59))
                .build();

        final AssignmentDO assignmentDO2 = AssignmentDO.builder()
                .id(2L)
                .name("Example2")
                .description("Description2")
                .startDate(LocalDateTime.of(2025, 9, 15, 9, 0))
                .expirationDate(LocalDateTime.of(2025, 10, 15, 23, 59))
                .build();

        when(modelMapper.map(assignmentDO1, AssignmentDTO.class)).thenReturn(assignmentDTO1);
        when(modelMapper.map(assignmentDO2, AssignmentDTO.class)).thenReturn(assignmentDTO2);
        final List<AssignmentDO> assignmentDOS = List.of(assignmentDO1, assignmentDO2);
        when(assignmentMapper.toAssignmentDTO(assignmentDO1)).thenReturn(assignmentDTO1);
        when(assignmentMapper.toAssignmentDTO(assignmentDO2)).thenReturn(assignmentDTO2);

        final List<AssignmentDTO> result = assignmentMapper.toAssignmentDTOList(assignmentDOS);

        assertEquals(List.of(assignmentDTO1, assignmentDTO2), result);
        verify(modelMapper).map(assignmentDO1, AssignmentDTO.class);
        verify(modelMapper).map(assignmentDO2, AssignmentDTO.class);
        verify(assignmentMapper).toAssignmentDTO(assignmentDO1);

    }
}