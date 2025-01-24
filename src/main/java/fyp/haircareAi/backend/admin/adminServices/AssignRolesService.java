package fyp.haircareAi.backend.admin.adminServices;


import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignRolesService {
    @Autowired
    private AuthRepo authRepo;


    public UserEntity assignAsAdmin(String email){
        Optional<UserEntity> dbuser= authRepo.findByEmail(email);

        if(dbuser.isPresent()){
            UserEntity user=dbuser.get();
            user.setRole(UserEntity.Role.valueOf("ADMIN"));
           return authRepo.save(user);
        }
        return null;
    }

    public UserEntity deAssignAdmin(String email){
        Optional<UserEntity> dbuser= authRepo.findByEmail(email);

        if(dbuser.isPresent()){
            UserEntity user=dbuser.get();
            user.setRole(UserEntity.Role.valueOf("USER"));
            return authRepo.save(user);
        }
        return null;
    }
}
