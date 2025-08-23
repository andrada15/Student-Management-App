package ro.scoalainformala.studentmgmt.teacher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import ro.scoalainformala.studentmgmt.courses.CourseDO;
import ro.scoalainformala.studentmgmt.user.UserDO;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "teachers")
public class TeacherDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CourseDO course;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserDO user;
}
