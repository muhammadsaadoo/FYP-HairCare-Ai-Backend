package fyp.haircareAi.backend.user.services.interfaces;

import fyp.haircareAi.backend.user.entities.LoginEntity;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    public ResponseEntity<?> checkUser(LoginEntity user);
}
