package hibernate.entity;

import lombok.*;

import javax.persistence.*;

@Data
@ToString(exclude = "course")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private StudentProfile studentProfile;

}

