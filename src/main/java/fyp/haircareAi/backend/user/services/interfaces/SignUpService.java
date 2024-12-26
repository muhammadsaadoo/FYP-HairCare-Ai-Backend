package fyp.haircareAi.backend.user.services.interfaces;

import fyp.haircareAi.backend.user.entities.UserEntity;
import org.springframework.validation.BindingResult;

public interface SignUpService {
    public Object insertUser(String code);
    public Object verifyEmail(UserEntity newUser, BindingResult result);
}
