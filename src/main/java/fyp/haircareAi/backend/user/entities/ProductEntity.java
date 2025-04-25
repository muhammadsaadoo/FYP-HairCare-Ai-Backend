package fyp.haircareAi.backend.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Products")
@Data
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long productId;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private ProblemEntity problem;

    @Column(columnDefinition = "TEXT")
    private String description;



    @Column
    private Double rating;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer recommendations = 0;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Status {
        Available, Discontinued
    }


    @Column
    private String imagePath;

    @Column
    List<String> comments=new ArrayList<>();



}
