package ro.scoalainformala.studentmgmt.teacher;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeacherMapperImplUnitTest {

    @Mock
    private ModelMapper modelMapper;

    @Spy
    @InjectMocks
    private TeacherMapperImpl teacherMapper;

    @BeforeAll
    void beforeAll() {
        openMocks(this);
    }

    @Test
    void mapDoToDTO() {
        final TeacherDO teacherDO = TeacherDO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        final TeacherDTO mockedTeacherDTO = TeacherDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        when(modelMapper.map(teacherDO, TeacherDTO.class)).thenReturn(mockedTeacherDTO);
        final TeacherDTO result = teacherMapper.mapDoToDTO(teacherDO);

        assertEquals(mockedTeacherDTO, result);
        verify(modelMapper).map(teacherDO, TeacherDTO.class);
        verifyNoMoreInteractions(modelMapper);
    }

    @Test
    void mapDtoToDO() {
        final TeacherDTO teacherDTO = TeacherDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        final TeacherDO mockedTeacherDO = TeacherDO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        when(modelMapper.map(teacherDTO, TeacherDO.class)).thenReturn(mockedTeacherDO);
        final TeacherDO result = teacherMapper.mapDtoToDO(teacherDTO);

        assertEquals(mockedTeacherDO, result);
        verify(modelMapper).map(teacherDTO, TeacherDO.class);
        verifyNoMoreInteractions(modelMapper);
    }

    @Test
    void mapListDoToDTO() {
        final TeacherDO teacherDO1 = TeacherDO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        final TeacherDO teacherDO2 = TeacherDO.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@school.edu")
                .build();
        final TeacherDTO teacherDTO1 = TeacherDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        final TeacherDTO teacherDTO2 = TeacherDTO.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@school.edu")
                .build();

        when(modelMapper.map(teacherDO1, TeacherDTO.class)).thenReturn(teacherDTO1);
        when(modelMapper.map(teacherDO2, TeacherDTO.class)).thenReturn(teacherDTO2);
        final List<TeacherDO> teacherDOs = List.of(teacherDO1, teacherDO2);
        when(teacherMapper.mapDoToDTO(teacherDO1)).thenReturn(teacherDTO1);
        when(teacherMapper.mapDoToDTO(teacherDO2)).thenReturn(teacherDTO2);

        final List<TeacherDTO> result = teacherMapper.mapListDoToDTO(teacherDOs);

        assertEquals(List.of(teacherDTO1, teacherDTO2), result);
        verify(modelMapper).map(teacherDO1, TeacherDTO.class);
        verify(modelMapper).map(teacherDO2, TeacherDTO.class);
        verify(teacherMapper).mapDoToDTO(teacherDO1);
    }

    @Test
    void mapListDtoToDO() {
        final TeacherDTO teacherDTO1 = TeacherDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        final TeacherDTO teacherDTO2 = TeacherDTO.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@school.edu")
                .build();
        final TeacherDO teacherDO1 = TeacherDO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@school.edu")
                .build();
        final TeacherDO teacherDO2 = TeacherDO.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@school.edu")
                .build();

        when(modelMapper.map(teacherDTO1, TeacherDO.class)).thenReturn(teacherDO1);
        when(modelMapper.map(teacherDTO2, TeacherDO.class)).thenReturn(teacherDO2);
        final List<TeacherDTO> teacherDTOs = List.of(teacherDTO1, teacherDTO2);
        when(teacherMapper.mapDtoToDO(teacherDTO1)).thenReturn(teacherDO1);
        when(teacherMapper.mapDtoToDO(teacherDTO2)).thenReturn(teacherDO2);

        final List<TeacherDO> result = teacherMapper.mapListDtoToDO(teacherDTOs);

        assertEquals(List.of(teacherDO1, teacherDO2), result);
        verify(modelMapper).map(teacherDTO1, TeacherDO.class);
        verify(modelMapper).map(teacherDTO2, TeacherDO.class);
        verify(teacherMapper).mapDtoToDO(teacherDTO1);
    }
}



