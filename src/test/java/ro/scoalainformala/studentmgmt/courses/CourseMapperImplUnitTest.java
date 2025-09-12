package ro.scoalainformala.studentmgmt.courses;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseMapperImplUnitTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CourseMapperImpl courseMapperImpl;

    @BeforeAll
    void beforeAll() {
        openMocks(this);
    }


    @Test
    void testMapDoToDto() {
        CourseDTO courseDTO = CourseDTO.builder().id(1L).name("Course 1").build();
        CourseDO courseDO = CourseDO.builder().id(1L).name("Course 1").build();

        when (modelMapper.map(courseDO, CourseDTO.class)).thenReturn(courseDTO);
        CourseDTO result = courseMapperImpl.mapDoToDto(courseDO);

        assertEquals(courseDTO, result);
        verify(modelMapper).map(courseDO, CourseDTO.class);
        verifyNoMoreInteractions(modelMapper);
    }

    @Test
    void testMapDtoToDo() {
        CourseDTO courseDTO = CourseDTO.builder().id(1L).name("Course 1").build();
        CourseDO courseDO = CourseDO.builder().id(1L).name("Course 1").build();

        when (modelMapper.map(courseDTO, CourseDO.class)).thenReturn(courseDO);
        CourseDO result = courseMapperImpl.mapDtoToDo(courseDTO);

        assertEquals(courseDO, result);
        verify(modelMapper).map(courseDTO, CourseDO.class);
        verifyNoMoreInteractions(modelMapper);
    }

    @Test
    void testMapDoListToDto() {
        CourseDTO courseDTO1 = CourseDTO.builder().id(1L).name("Course 1").build();
        CourseDTO courseDTO2 = CourseDTO.builder().id(2L).name("Course 2").build();
        CourseDO courseDO1 = CourseDO.builder().id(1L).name("Course 1").build();
        CourseDO courseDO2 = CourseDO.builder().id(2L).name("Course 2").build();

        when(modelMapper.map(courseDO1, CourseDTO.class)).thenReturn(courseDTO1);
        when(modelMapper.map(courseDO2, CourseDTO.class)).thenReturn(courseDTO2);
        final List<CourseDO> courses = List.of(courseDO1, courseDO2);
        when(courseMapperImpl.mapDoToDto(courseDO1)).thenReturn(courseDTO1);
        when(courseMapperImpl.mapDoToDto(courseDO2)).thenReturn(courseDTO2);

        final List<CourseDTO> result = courseMapperImpl.mapDoListToDto(courses);

        assertEquals(List.of(courseDTO1, courseDTO2), result);
        verify(modelMapper).map(courseDTO1, CourseDTO.class);
        verify(modelMapper).map(courseDTO2, CourseDTO.class);
        verifyNoMoreInteractions(modelMapper);
    }

    @Test
    void testMapDtoListToDo() {
        CourseDTO courseDTO1 = CourseDTO.builder().id(1L).name("Course 1").build();
        CourseDTO courseDTO2 = CourseDTO.builder().id(2L).name("Course 2").build();
        CourseDO courseDO1 = CourseDO.builder().id(1L).name("Course 1").build();
        CourseDO courseDO2 = CourseDO.builder().id(2L).name("Course 2").build();

        when(modelMapper.map(courseDTO1, CourseDO.class)).thenReturn(courseDO1);
        when(modelMapper.map(courseDTO2, CourseDO.class)).thenReturn(courseDO2);
        final List<CourseDTO> courses = List.of(courseDTO1, courseDTO2);
        when(courseMapperImpl.mapDtoToDo(courseDTO1)).thenReturn(courseDO1);
        when(courseMapperImpl.mapDtoToDo(courseDTO2)).thenReturn(courseDO2);

        final List<CourseDO> result = courseMapperImpl.mapDtoListToDo(courses);

        assertEquals(List.of(courseDTO1, courseDTO2), result);
        verify(modelMapper).map(courseDTO1, CourseDTO.class);
        verify(modelMapper).map(courseDTO2, CourseDTO.class);
        verifyNoMoreInteractions(modelMapper);
    }
}