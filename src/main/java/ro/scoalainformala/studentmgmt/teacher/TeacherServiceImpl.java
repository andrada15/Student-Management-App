package ro.scoalainformala.studentmgmt.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.scoalainformala.studentmgmt.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public List<TeacherDTO> getAllTeachers() {
        final List<TeacherDO> list = teacherRepository.findAll();
        return teacherMapper.mapListDoToDTO(list);
    }

    @Override
    public TeacherDTO getTeacherById(Long id) {
        return teacherRepository.findById(id).map(teacherMapper::mapDoToDTO)
                .orElseThrow(() -> new NotFoundException("Teacher wit id " + id + "not found"));
    }

    @Override
    public TeacherDTO createTeacher(TeacherDTO teacher) {
        final TeacherDO teacherDO = teacherMapper.mapDtoToDO(teacher);
        final TeacherDO createdTeacher = teacherRepository.save(teacherDO);
        return teacherMapper.mapDoToDTO(createdTeacher);
    }

    @Override
    public TeacherDTO updateTeacher(final Long id, TeacherDTO teacher) {
        final Optional<TeacherDO> teacherDO = teacherRepository.findById(teacher.getId());
        return teacherDO.map(teacherFromDB -> {
            copyPropertiesFromDtoToDo(teacher, teacherFromDB);
            final TeacherDO updatedTeacher = teacherRepository.save(teacherFromDB);
            return teacherMapper.mapDoToDTO(updatedTeacher);
        })
                .orElseThrow(() -> new NotFoundException("Teacher wit id " + id + "not found"));
    }

    @Override
    public void deleteTeacher(Long id) {
        final TeacherDO teacherDO = teacherRepository.getById(id);
        teacherRepository.delete(teacherDO);
    }

    private void copyPropertiesFromDtoToDo(final TeacherDTO teacherDTO, final TeacherDO teacherFromDB) {
        teacherFromDB.setFirstName(teacherFromDB.getFirstName());
        teacherFromDB.setLastName(teacherFromDB.getLastName());
        teacherFromDB.setEmail(teacherFromDB.getEmail());
        teacherFromDB.setPhone(teacherFromDB.getPhone());
        teacherFromDB.setDepartment(teacherFromDB.getDepartment());
        teacherFromDB.setCourse(teacherFromDB.getCourse());
        teacherFromDB.setUser(teacherFromDB.getUser());
    }

}
