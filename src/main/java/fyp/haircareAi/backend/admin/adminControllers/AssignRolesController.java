package fyp.haircareAi.backend.admin.adminControllers;


import fyp.haircareAi.backend.admin.adminServices.AssignRolesService;
import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.services.interfaces.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/roles")
public class AssignRolesController {
    @Autowired
    private SignUpService signUpService;
    @Autowired
    private AssignRolesService adminService;
    @PostMapping("/assign-admin/{email}")
    public Object assignRole(@PathVariable  String email) {
        UserEntity updatedUser=adminService.assignAsAdmin(email);
        if(updatedUser != null){
            return updatedUser;
        }
        return "user not found";


    }
    @PostMapping("/remove-admin/{email}")
    public Object deAssignRole(@PathVariable  String email) {
        UserEntity updatedUser=adminService.deAssignAdmin(email);
        if(updatedUser != null){
            return updatedUser;
        }
        return "user not found";


    }
}

