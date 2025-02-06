package fyp.haircareAi.backend.admin.adminControllers;




import fyp.haircareAi.backend.admin.adminServices.AdminUserService;
import fyp.haircareAi.backend.admin.adminServices.UserDashboardService;
import fyp.haircareAi.backend.admin.cache.UserCache;
import fyp.haircareAi.backend.user.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/admin/dashboard/usersdashboard")
@RestController
//@CrossOrigin(origins = "http://localhost:5173")
public class AdminUsersController {
    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private UserDashboardService userDashboardService;

    @GetMapping
    public ResponseEntity<Map<String, Object>>getAdminDashboardDataForUsers(){
        Map<String, Object> userData=userDashboardService.getUserDashboardData();
        return ResponseEntity.ok(userData);

    }

    @GetMapping("/allusers")
    public Object getAlUser() {
        System.out.println("users call");
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
    @GetMapping("/getuserbyemail/{email}")
    public Object getUserByEmail(@PathVariable String email) {
        UserEntity user = adminUserService.findUserByEmail(email);
        if ( user !=null) {
            return user;
        } else {
            return "no user found of email :"+email;
        }
    }

    @DeleteMapping("/banuserbyemail/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {

        UserEntity  user = adminUserService.findUserByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();

        }
        else if(adminUserService.deleteUser(user)){

        return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/activeusers")
    public List<UserEntity> getAllActiveUsers() {
        return adminUserService.activeUsers();

    }



}
