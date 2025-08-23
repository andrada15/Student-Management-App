package ro.scoalainformala.studentmgmt.role;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface  RoleRepository extends JpaRepository<RoleDO, Long> {

     @Query("SELECT r FROM roles r WHERE LOWER(r.roleName) = LOWER(:roleName)")
     Optional<RoleDO> findByRoleName(String roleName);
}
