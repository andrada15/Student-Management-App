package ro.scoalainformala.studentmgmt.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ro.scoalainformala.studentmgmt.role.RoleDO;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDetailsServiceImplUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @BeforeAll
    void beforeAll() {
        openMocks(this);
    }

    @AfterEach
    void afterEach() {
        reset(userRepository);
    }

    @Test
    void testLoadUserByUsername() {
        final String username = "admin@school.edu";
        final String password = "$2a$10$2W7PUFzE6C7E6zX2xNbbIezUwGq22Xg5Y2Rkc5wTu1sMg1K.QnSYK";
        final RoleDO role = RoleDO.builder().id(1L).roleName("ADMIN").build();
        final UserDO userDO = UserDO.builder()
                .id(1L)
                .username(username)
                .password(password)
                .role(role)
                .build();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userDO));



        final UserDetails result = userDetailsServiceImpl.loadUserByUsername(username);

        assertEquals(username, result.getUsername());
        assertEquals(password, result.getPassword());
        assertEquals(1, result.getAuthorities().size());
        assertEquals("ROLE_ADMIN", result.getAuthorities().iterator().next().getAuthority());

        verify(userRepository).findByUsername(username);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testLoadUsernameNotFound() {
        final String username = "unknown_user";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsServiceImpl.loadUserByUsername(username));

        verify(userRepository).findByUsername(username);
        verifyNoMoreInteractions(userRepository);
    }
}