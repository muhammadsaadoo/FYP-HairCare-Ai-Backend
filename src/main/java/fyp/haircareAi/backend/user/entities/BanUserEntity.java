package fyp.haircareAi.backend.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "banuser")
@Data
@NoArgsConstructor
public class BanUserEntity {

    @Id
    @Column(unique = true)
    @NonNull
    private String email;
}
