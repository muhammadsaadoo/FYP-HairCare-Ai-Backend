package fyp.haircareAi.backend.user.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "Issues")
@Data
@NoArgsConstructor
public class IssueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long issueId;


    @Column
    private long userId;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status = Status.Pending;

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Status {
        Pending, Resolved
    }
    @Column
    @Enumerated(EnumType.STRING)
    private FeedbackEntity.Type type;

    public enum Type {
        Product, AI, App
    }
}
