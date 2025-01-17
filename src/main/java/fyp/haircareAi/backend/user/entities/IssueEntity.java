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
@Table(name = "Issues")
@Data
@NoArgsConstructor
public class IssueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long issueId;


    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

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
}
