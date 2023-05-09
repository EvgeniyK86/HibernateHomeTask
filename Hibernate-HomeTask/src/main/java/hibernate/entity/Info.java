package hibernate.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "info")
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


    public void setCourse(Course course) {
        this.course = course;
        this.course.getInfos().add(this);
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
        this.trainer.getInfos().add(this);
    }


}
