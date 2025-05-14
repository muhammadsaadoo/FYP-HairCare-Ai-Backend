package fyp.haircareAi.backend.admin.adminServices;


import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignRolesService {
    @Autowired
    private AuthRepo authRepo;


    public ResponseEntity<UserEntity> assignAsAdmin(String email){
        try {
            Optional<UserEntity> dbuser = authRepo.findByEmail(email);

            if (dbuser.isPresent()) {
                UserEntity user = dbuser.get();
                user.setRole(UserEntity.Role.valueOf("ADMIN"));
                return ResponseEntity.ok(authRepo.save(user));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<UserEntity> deAssignAdmin(String email){
        try {
            Optional<UserEntity> dbuser = authRepo.findByEmail(email);

            if (dbuser.isPresent()) {
                UserEntity user = dbuser.get();
                user.setRole(UserEntity.Role.valueOf("USER"));
                return ResponseEntity.ok(authRepo.save(user));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}
