package fyp.haircareAi.backend.admin.adminServices;


import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignRolesService {
    @Autowired
    private AuthRepo authRepo;


    public UserEntity assignAsAdmin(String email){
        UserEntity user=authRepo.findByEmail(email);
        if(user != null){
            user.setRole(UserEntity.Role.valueOf("ADMIN"));
           return authRepo.save(user);
        }
        return null;
    }

    public UserEntity deAssignAdmin(String email){
        UserEntity user=authRepo.findByEmail(email);
        if(user != null){
            user.setRole(UserEntity.Role.valueOf("USER"));
            return authRepo.save(user);
        }
        return null;
    }
}
