package ro.scoalainformala.studentmgmt.authentication;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.scoalainformala.studentmgmt.config.JwtService;
import ro.scoalainformala.studentmgmt.role.RoleDO;
import ro.scoalainformala.studentmgmt.role.RoleRepository;
import ro.scoalainformala.studentmgmt.student.StudentDO;
import ro.scoalainformala.studentmgmt.student.StudentRepository;
import ro.scoalainformala.studentmgmt.teacher.TeacherDO;
import ro.scoalainformala.studentmgmt.teacher.TeacherRepository;
import ro.scoalainformala.studentmgmt.user.UserDO;
import ro.scoalainformala.studentmgmt.user.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    Authentication authentication;

    @BeforeEach
    void beforeEach() {
        openMocks(this);
    }

    @AfterEach
    void afterEach() {
        reset(authenticationManager, jwtService, studentRepository, teacherRepository, roleRepository);
    }

    @Test
    void testAuthenticateValidCredentials() {
        AuthenticationRequest request = new AuthenticationRequest("user@test.com", "password", "STUDENT");

        Authentication authentication = mock(Authentication.class);
        Collection<? extends GrantedAuthority> authorities =
                Collections.singletonList((GrantedAuthority) () -> "ROLE_STUDENT");


        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);
        when(jwtService.generateToken(eq("user@test.com"), anyList())).thenReturn("jwt-token");

        AuthenticationResponse response = authenticationService.authenticate(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(eq("user@test.com"), anyList());
    }

    @Test
    void testRegisterRoleStudent() {
        AuthenticationRequest request = new AuthenticationRequest("student@test.com", "password", "STUDENT");

        when(userRepository.findByUsername(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPass");

        RoleDO role = RoleDO.builder().roleName("STUDENT").build();
        when(roleRepository.findByRoleName("STUDENT")).thenReturn(Optional.of(role));
        when(jwtService.generateToken(eq("student@test.com"), anyList())).thenReturn("jwt-token");

        AuthenticationResponse response = authenticationService.register(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        verify(userRepository).save(any(UserDO.class));
        verify(studentRepository).save(any(StudentDO.class));
        verify(teacherRepository, never()).save(any(TeacherDO.class));
    }

    @Test
    void testRegisterRoleTeacher() {
        AuthenticationRequest request = new AuthenticationRequest("teacher@test.com", "password", "TEACHER");

        when(userRepository.findByUsername(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPass");

        RoleDO role = RoleDO.builder().roleName("TEACHER").build();
        when(roleRepository.findByRoleName("TEACHER")).thenReturn(Optional.of(role));
        when(jwtService.generateToken(eq("teacher@test.com"), anyList())).thenReturn("jwt-token");

        AuthenticationResponse response = authenticationService.register(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        verify(userRepository).save(any(UserDO.class));
        verify(teacherRepository).save(any(TeacherDO.class));
        verify(studentRepository, never()).save(any(StudentDO.class));
    }

    @Test
    void testRegisterUserAlreadyExists() {
        AuthenticationRequest request = new AuthenticationRequest("existing@test.com", "password", "STUDENT");

        when(userRepository.findByUsername(request.getEmail())).thenReturn(Optional.of(new UserDO()));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> authenticationService.register(request));
        assertEquals("Username already exists", ex.getMessage());
        verify(userRepository, never()).save(any(UserDO.class));
    }

    @Test
    void testRegisterRoleNotFound() {
        AuthenticationRequest request = new AuthenticationRequest("user@test.com", "password", "UNKNOWN");

        when(userRepository.findByUsername(request.getEmail())).thenReturn(Optional.empty());
        when(roleRepository.findByRoleName("UNKNOWN")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> authenticationService.register(request));
        assertEquals("Role not found: UNKNOWN", ex.getMessage());
    }
}