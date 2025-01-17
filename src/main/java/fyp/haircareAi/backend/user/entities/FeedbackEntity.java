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
@Table(name = "Feedback")
@Data
@NoArgsConstructor
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long feedbackId;

    @Column
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column
    private Long referenceId;
    // Links to Products or HairAnalysis
    @Column
    private Integer rating;

    @Column
    private String comments;

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Type {
        Product, AI, App
    }





}
