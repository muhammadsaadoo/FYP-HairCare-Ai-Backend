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
@Table(name = "Products")
@Data
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long productId;

    @Column
    private String name;

    @Column
    private String category;

    @Column
    private Double rating;
    @Column
    private Double price;

    @Column
    private Integer recommendations = 0;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status = Status.Available;

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Status {
        Available, Discontinued
    }
}
