package ro.scoalainformala.studentmgmt.authentication;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerUnitTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController authController;

    @BeforeAll
    void beforeAll() {
        openMocks(this);
    }

    @AfterEach
    void afterEach() {
        reset(authenticationService);
    }

    @Test
    void testRegisterSuccess() {
        AuthenticationRequest request = new AuthenticationRequest("john@school.edu", "password123","TEACHER");
        AuthenticationResponse expectedResponse = new AuthenticationResponse("token123");

        when(authenticationService.register(request)).thenReturn(expectedResponse);

        ResponseEntity<AuthenticationResponse> result = authController.register(request);
        assertEquals(200, result.getStatusCode().value());
        assertEquals("token123", result.getBody().getToken());

        verify(authenticationService).register(request);
        verifyNoMoreInteractions(authenticationService);
    }

    @Test
    void testRegisterFailure() {
        AuthenticationRequest request = new AuthenticationRequest("john@school.edu", "password123","TEACHER");

        when(authenticationService.register(request)).thenThrow(new RuntimeException("Registration failed"));

        assertThrows(RuntimeException.class, () -> authController.register(request));

        verify(authenticationService).register(request);
        verifyNoMoreInteractions(authenticationService);
    }

    @Test
    void testLoginSuccess() {
        AuthenticationRequest request = new AuthenticationRequest("john@school.edu", "password123","TEACHER");
        AuthenticationResponse authResponse = new AuthenticationResponse("jwt_token_456");

        when(authenticationService.authenticate(request)).thenReturn(authResponse);

        ResponseEntity<AuthenticationResponse> result = authController.login(request);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("jwt_token_456", result.getBody().getToken());

        verify(authenticationService).authenticate(request);
        verifyNoMoreInteractions(authenticationService);
    }

    @Test
    void testLoginFailure() {
        AuthenticationRequest request = new AuthenticationRequest("hacker@school.edu", "wrongpass", "TEACHER");

        when(authenticationService.authenticate(request)).thenThrow(new RuntimeException("Login failed"));

        assertThrows(RuntimeException.class, () -> authController.login(request));

        verify(authenticationService).authenticate(request);
        verifyNoMoreInteractions(authenticationService);

    }

    @Test
    void testCreateUserSuccess() {
        AuthenticationRequest request = new AuthenticationRequest("admin@school.edu", "password123","ADMIN");
        AuthenticationResponse expectedResponse = new AuthenticationResponse("token_admin");

        when(authenticationService.register(request)).thenReturn(expectedResponse);


        ResponseEntity<AuthenticationResponse> result = authController.createUser(request);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("token_admin", result.getBody().getToken());

        verify(authenticationService).register(request);
        verifyNoMoreInteractions(authenticationService);
    }

    @Test
    void testCreateUserFailure() {
        AuthenticationRequest request = new AuthenticationRequest("bad_admin@school.edu", "password123", "ADMIN");
        when(authenticationService.register(request)).thenThrow(new RuntimeException("Create user failed"));

        assertThrows(RuntimeException.class, () -> authController.createUser(request));

        verify(authenticationService).register(request);
        verifyNoMoreInteractions(authenticationService);
    }
}