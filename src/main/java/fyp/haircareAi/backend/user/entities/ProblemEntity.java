package fyp.haircareAi.backend.user.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Problem")
@Data
@NoArgsConstructor
public class ProblemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long problemId; // Unique identifier for the problem

    @Column(name = "problem_name", nullable = false, unique = true, length = 255)
    private String problemName; // Name of the problem (e.g., Alopecia Areata)
}
