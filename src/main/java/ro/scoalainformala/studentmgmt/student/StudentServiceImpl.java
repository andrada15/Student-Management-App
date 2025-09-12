package ro.scoalainformala.studentmgmt.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.scoalainformala.studentmgmt.courses.CourseDO;
import ro.scoalainformala.studentmgmt.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(final StudentRepository studentRepository, final StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }


    @Override
    public List<StudentDTO> getAllStudents() {
        final List<StudentDO> studentDOS = studentRepository.findAll();
        return studentMapper.mapDoToDtoList(studentDOS);
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::mapDoToDto)
                .orElseThrow(() -> new NotFoundException("Student with id" + id + "not found"));
    }

    @Override
    public StudentDTO addStudent(StudentDTO student) {
        final StudentDO studentDO = studentMapper.mapDtoToDo(student);
        final StudentDO savedStudentDO = studentRepository.save(studentDO);
        return studentMapper.mapDoToDto(savedStudentDO);
    }

    @Override
    public StudentDTO updateStudent(final Long id, StudentDTO student) {
        final Optional<StudentDO> studentDO = studentRepository.findById(student.getId());
        return studentDO.map(studentsFromDB -> {
            copyPropertiesFromDtoToDo(student, studentsFromDB);
            final StudentDO savedStudent = studentRepository.save(studentsFromDB);
            return studentMapper.mapDoToDto(savedStudent);
        })
                .orElseThrow(() -> new NotFoundException("Student not found with id: " + id));
    }

    @Override
    public StudentDO getStudentByEmail(String email) {
        return studentRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("The student with email: " + email + "not found"));
    }

    @Override
    public void deleteStudent(Long id) {
        final StudentDO studentDO = studentRepository.getById(id);
        studentRepository.delete(studentDO);
    }

    private void copyPropertiesFromDtoToDo(final StudentDTO studentDTO, final StudentDO studentFromDB) {
        studentFromDB.setAddress(studentDTO.getAddress());
        studentFromDB.setEmail(studentDTO.getEmail());
        studentFromDB.setFirstName(studentDTO.getFirstName());
        studentFromDB.setLastName(studentDTO.getLastName());
        studentFromDB.setPhone(studentDTO.getPhone());
        studentFromDB.setDepartmentId(studentDTO.getDepartmentId());
        studentFromDB.setCollegeYear(studentDTO.getCollegeYear());
        studentFromDB.setCourse((CourseDO) studentDTO.getCourses());
    }
}
