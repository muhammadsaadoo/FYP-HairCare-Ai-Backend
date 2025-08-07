package fyp.haircareAi.backend.user.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "HairAnalysis")
@Data
@NoArgsConstructor
public class HairAnalysisEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analysis_id")
    private Long analysisId;


    @Column
    private long userId; // One user can have multiple hair analyses

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

   @Column
    private String problem; // One problem can be linked to multiple analyses

    @Column(name = "recommended_product")
    private String recommendedProduct;

    @Column(name = "image_path")
    private String imagePath;
}
