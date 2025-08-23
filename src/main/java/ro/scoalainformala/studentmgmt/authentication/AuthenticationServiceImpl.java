package ro.scoalainformala.studentmgmt.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.scoalainformala.studentmgmt.config.JwtService;
import ro.scoalainformala.studentmgmt.role.RoleDO;
import ro.scoalainformala.studentmgmt.role.RoleRepository;
import ro.scoalainformala.studentmgmt.student.StudentDO;
import ro.scoalainformala.studentmgmt.student.StudentRepository;
import ro.scoalainformala.studentmgmt.teacher.TeacherDO;
import ro.scoalainformala.studentmgmt.teacher.TeacherRepository;
import ro.scoalainformala.studentmgmt.user.UserDO;
import ro.scoalainformala.studentmgmt.user.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder,  StudentRepository studentRepository, TeacherRepository teacherRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public AuthenticationResponse authenticate(final AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = jwtService.generateToken(request.getEmail(), roles);
        return new AuthenticationResponse(token);
    }

    @Override
    public AuthenticationResponse register(AuthenticationRequest request) {

        if (userRepository.findByUsername(request.getEmail()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        RoleDO role = roleRepository.findByRoleName(request.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found: " + request.getRoleName()));



        UserDO user = UserDO.builder()
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();


        userRepository.save(user);

       if ("STUDENT".equalsIgnoreCase(role.getRoleName())) {
           createStudentProfile(request, user);
       } else if ("TEACHER".equalsIgnoreCase(role.getRoleName())) {
           createTeacherProfile(request, user);
       }
        List<String> roles = Collections.singletonList("ROLE_" + role.getRoleName());
        String token = jwtService.generateToken(request.getEmail(), roles);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }


    private void createStudentProfile(AuthenticationRequest request, UserDO user) {
        StudentDO student = StudentDO.builder()
                .email(request.getEmail())
                .user(user)
                .build();
        studentRepository.save(student);
    }

    private void createTeacherProfile(AuthenticationRequest request, UserDO user) {
        TeacherDO teacher = TeacherDO.builder()
                .email(request.getEmail())
                .user(user)
                .build();

        teacherRepository.save(teacher);
    }
}
