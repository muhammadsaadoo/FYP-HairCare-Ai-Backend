package fyp.haircareAi.backend.user.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Indexed;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column

    private long id;

    @Column
    @NonNull
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String first_name;


    @Column
    @NonNull
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String last_name;



    @Column(unique = true)
    @NonNull
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Email should be valid")
    private String email;

    @Column
    @NonNull
    @NotBlank(message = "Password is mandatory")
    @Size(min = 2, max = 100, message = "hashed password should be between 2 and 100 characters")
    private String password;

    @Column
    @NonNull
    private String gender;

    @NonNull
    @Column
    private Integer age;


    @Column
    private String hairType;

    @Column
    private String hairIssues;

    @Column
    private String skinType;

    @Column
    @Enumerated(EnumType.STRING)
    @NonNull
    private Role role;

    // Enum to represent user roles
    public enum Role {
        USER,
        ADMIN
    }




}
