package ro.scoalainformala.studentmgmt.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.scoalainformala.studentmgmt.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        List<CourseDO> list =  courseRepository.findAll();
        return courseMapper.mapDoListToDto(list);
    }

    @Override
    public CourseDTO getCourseById(final Long id) {
        return courseRepository.findById(id)
                .map(courseMapper::mapDoToDto)
                .orElseThrow(() -> new NotFoundException("The course" + id + "doesn't exist"));
    }

    @Override
    public CourseDTO addCourse(CourseDTO courseDto) {
        final CourseDO courseDO = courseMapper.mapDtoToDo(courseDto);
        final CourseDO savedCourse = courseRepository.save(courseDO);
        return courseMapper.mapDoToDto(savedCourse);
    }

    @Override
    public CourseDTO updateCourse(final Long id, CourseDTO course) {
        final Optional<CourseDO> courseDo = courseRepository.findById(id);
        return courseDo.map(courseFromDB -> {
            copyPropertiesFromDtoToDo(course, courseFromDB);
            final CourseDO savedCourse = courseRepository.save(courseFromDB);
            return courseMapper.mapDoToDto(savedCourse);
        })
                .orElseThrow(() -> new NotFoundException("Course not found with id: " + id));
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public CourseDTO getCourseByName(final String name) {
        final CourseDO courseName = courseRepository.findByName(name);
        return courseMapper.mapDoToDto(courseName);
    }

    private void copyPropertiesFromDtoToDo(final CourseDTO courseDTO, final CourseDO courseFromDB) {
        courseFromDB.setName(courseDTO.getName());
        courseFromDB.setStudents(courseDTO.getStudent());
        courseFromDB.setTeacher(courseDTO.getTeacher());
    }
}
