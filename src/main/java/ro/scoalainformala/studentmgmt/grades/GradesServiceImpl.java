package ro.scoalainformala.studentmgmt.grades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GradesServiceImpl implements GradesService {

    private final GradesRepository gradesRepository;
    private final GradesMapper gradesMapper;

    @Autowired
    public GradesServiceImpl(GradesRepository gradesRepository, GradesMapper gradesMapper) {
        this.gradesRepository = gradesRepository;
        this.gradesMapper = gradesMapper;
    }


    @Override
    public List<GradesDTO> getAllGradesByStudentId(Long studentId) {
        final List<GradesDO> gradesDoByStudentId = gradesRepository.findGradesByStudentId(studentId);
        return gradesDoByStudentId.stream()
                .map(gradesMapper::toGradesDTO)
                .toList();
    }
}
