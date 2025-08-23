package ro.scoalainformala.studentmgmt.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    Logger logger = Logger.getLogger(String.valueOf(UserDetailsServiceImpl.class));

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Attempting to load user: " + username);

        final UserDO userDO = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        logger.info("User found: " + userDO);
        logger.info("Stored password: " + userDO.getPassword().substring(0,10));
        logger.info("Role object: " + userDO.getRole());
        logger.info("Role name: " +
                (userDO.getRole() != null ? userDO.getRole().getRoleName() : "NULL"));
        String roleName = userDO.getRole().getRoleName();

        return User.builder()
                .username(userDO.getUsername())
                .password(userDO.getPassword())
                .authorities("ROLE_" + roleName)
                .build();
    }

}
