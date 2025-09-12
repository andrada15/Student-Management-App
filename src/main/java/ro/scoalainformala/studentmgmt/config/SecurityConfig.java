package ro.scoalainformala.studentmgmt.config;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configure(http))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/test/**").permitAll()
                        .requestMatchers("/web/login").permitAll()
                        .requestMatchers("/web/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/teacher/**").hasAnyRole("ADMIN","TEACHER")
                        .requestMatchers("/student/**").hasAnyRole("ADMIN")
                        .requestMatchers("/courses/**").hasAnyRole("ADMIN","TEACHER","STUDENT")
                        .requestMatchers("/student/profile").hasAnyRole("ADMIN","STUDENT")
                        .requestMatchers("/grades/**").hasAnyRole("ADMIN","TEACHER","STUDENT")
                        .requestMatchers("/assignments/**").hasAnyRole("ADMIN","TEACHER","STUDENT")
                        .anyRequest().authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            if (request.getRequestURI().startsWith("/api")) {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.getWriter().write("{\"error\":\"Unauthorized\"}");
                            } else {
                                response.sendRedirect("/web/login");
                            }
                        }))
                .build();



    }


    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
