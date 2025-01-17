package fyp.haircareAi.backend.admin.adminControllers;


import fyp.haircareAi.backend.admin.adminServices.AdminUserService;
import fyp.haircareAi.backend.admin.cache.UserCache;
import fyp.haircareAi.backend.user.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/users")
@RestController
public class AdminUsersController {
    @Autowired
    private AdminUserService adminUserService;

    @GetMapping
    public Object getAlUser() {
        List<UserEntity> entries = adminUserService.getAllUsers();
        if ( entries !=null && !entries.isEmpty() ) {
            return entries;
        } else {
            return "no user found";
        }
    }
    @GetMapping("/allAdmins")
    public Object allAdmins() {

        List<UserEntity> entries = adminUserService.getAllAdmins();
        if ( entries !=null && !entries.isEmpty() ) {
            return entries;
        } else {
            return "no user found";
        }
    }
    @GetMapping("/{email}")
    public Object getUserByEmail(@PathVariable String email) {
        UserEntity user = adminUserService.findUserByEmail(email);
        if ( user !=null) {
            return user;
        } else {
            return "no user found of email :"+email;
        }
    }

    @DeleteMapping("/{email}")
    public Object deleteUser(@PathVariable String email) {

        UserEntity  user = adminUserService.findUserByEmail(email);

        if (user!= null) {
            adminUserService.deleteUser(user);
            return " user deleted with email : "+user.getEmail();

        }
        return "no user found";
    }

    @GetMapping("/activeusers")
    public List<UserEntity> getAllActiveUsers() {
        return adminUserService.activeUsers();

    }



}
