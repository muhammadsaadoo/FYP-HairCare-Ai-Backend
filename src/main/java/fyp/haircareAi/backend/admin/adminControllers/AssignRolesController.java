package fyp.haircareAi.backend.admin.adminControllers;


import fyp.haircareAi.backend.admin.adminServices.AssignRolesService;
import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.services.interfaces.SignUpService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/roles")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AssignRolesController {
    @Autowired
    private SignUpService signUpService;
    @Autowired
    private AssignRolesService adminService;






//assign role as admin
    @PostMapping("/assign-admin/{email}")
    public ResponseEntity<UserEntity> assignRole(@PathVariable  String email) {
         return adminService.assignAsAdmin(email);

    }


//remove admin
    @PostMapping("/remove-admin/{email}")
    public ResponseEntity<UserEntity> deAssignRole(@PathVariable  String email) {
       return adminService.deAssignAdmin(email);


    }
}

