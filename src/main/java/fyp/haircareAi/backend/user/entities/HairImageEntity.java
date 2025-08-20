package fyp.haircareAi.backend.user.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "HairImageNew")
@Data
@NoArgsConstructor
public class HairImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long ImageId;

    @Column
    private long userId;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "result" , columnDefinition = "TEXT")
    private String result;



    // One user can have multiple hair analyses

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();




}
