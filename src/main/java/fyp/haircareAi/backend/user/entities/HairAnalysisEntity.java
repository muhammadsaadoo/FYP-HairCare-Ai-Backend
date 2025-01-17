package fyp.haircareAi.backend.user.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "HairAnalysis")
@Data
@NoArgsConstructor
public class HairAnalysisEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long analysisId;


    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    @Column
    private String hairDensity;

    @Column
    private String scalpHealth;

    @Column
    private String detectedIssues;

    @Column
    private LocalDateTime timestamp = LocalDateTime.now();
}
