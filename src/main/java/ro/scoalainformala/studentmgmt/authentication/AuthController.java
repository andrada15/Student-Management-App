package ro.scoalainformala.studentmgmt.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        log.info("Registration request received for email: {}", request.getEmail());
        try {
            AuthenticationResponse response = authenticationService.register(request);
            log.info("Registration successful for email: {}", request.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Registration failed for email: {}", request.getEmail(), e);
            throw e;
        }
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        log.info("Login request received for email: {}", request.getEmail());

        try {
            String token = authenticationService.authenticate(request).getToken();
            log.info("Login successful for email: {}", request.getEmail());
            return ResponseEntity.ok().body(new AuthenticationResponse(token));
        } catch (Exception e) {
            log.error("Login failed for email: {}", request.getEmail(), e);
            throw e;
        }
    }
}
