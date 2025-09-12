package ro.scoalainformala.studentmgmt.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDO, Long> {

    Optional<UserDO> findByUsername(String username);
}
