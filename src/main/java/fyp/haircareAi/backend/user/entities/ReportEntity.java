package fyp.haircareAi.backend.user.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reports")
@Data
@NoArgsConstructor
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long reportId;


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

//    public enum Type {
//       AI, App , Product
//    }
}
