package fyp.haircareAi.backend.user.services.interfaces;

import fyp.haircareAi.backend.dto.EmailVerificationRequest;
import fyp.haircareAi.backend.user.entities.UserEntity;
import org.springframework.validation.BindingResult;

public interface SignUpService {
    public Object insertUser(UserEntity user);
    public Object verifyEmail(EmailVerificationRequest email_verification, BindingResult result);
}
