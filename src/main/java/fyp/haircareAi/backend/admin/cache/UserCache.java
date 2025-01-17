package fyp.haircareAi.backend.admin.cache;

import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import fyp.haircareAi.backend.user.services.SignupServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component         //it define the class as bean
public class UserCache {
    @Autowired
    private AuthRepo signUpRepo;

    @Getter
    List<UserEntity> users;

//    @PostConstruct
    public void  getAllUsers(){
        users=signUpRepo.findAll();
    }

}
