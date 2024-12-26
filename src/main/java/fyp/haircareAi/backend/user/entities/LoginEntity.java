package fyp.haircareAi.backend.user.entities;
import lombok.Data;
import lombok.NonNull;


@Data
public class LoginEntity {


   @NonNull
    private String email;
    @NonNull
    private String password;







}

