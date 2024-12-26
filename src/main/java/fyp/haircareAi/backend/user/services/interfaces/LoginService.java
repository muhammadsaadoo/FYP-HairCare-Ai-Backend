package fyp.haircareAi.backend.user.services.interfaces;

import fyp.haircareAi.backend.user.entities.LoginEntity;

public interface LoginService {
    public String checkUser(LoginEntity user);
}
