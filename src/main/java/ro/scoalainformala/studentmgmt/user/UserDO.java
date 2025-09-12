package ro.scoalainformala.studentmgmt.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import ro.scoalainformala.studentmgmt.role.RoleDO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class UserDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Email
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RoleDO role;

}
