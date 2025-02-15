package fyp.haircareAi.backend.user.services;

import fyp.haircareAi.backend.user.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UserEntity user;
}
