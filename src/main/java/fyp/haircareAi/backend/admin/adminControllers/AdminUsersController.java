package fyp.haircareAi.backend.admin.adminControllers;




import fyp.haircareAi.backend.admin.adminServices.AdminUserService;
import fyp.haircareAi.backend.admin.adminServices.UserDashboardService;
import fyp.haircareAi.backend.admin.cache.UserCache;
import fyp.haircareAi.backend.user.entities.FeedbackEntity;
import fyp.haircareAi.backend.user.entities.HairAnalysisEntity;
import fyp.haircareAi.backend.user.entities.ReportEntity;
import fyp.haircareAi.backend.user.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/admin/dashboard/usersdashboard")
@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AdminUsersController {
    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private UserDashboardService userDashboardService;

//    @GetMapping
//    public ResponseEntity<Map<String, Object>>getAdminDashboardDataForUsers(){
//        Map<String, Object> userData=userDashboardService.getUserDashboardData();
//        return ResponseEntity.ok(userData);
//
//    }

//get all users
    @GetMapping("/allusers")
    public ResponseEntity<List<UserEntity>> getAlUser() {
         return adminUserService.getAllUsers();

    }

//get all admins
    @GetMapping("/allAdmins")
    public ResponseEntity<List<UserEntity>> allAdmins() {

        return adminUserService.getAllAdmins();

    }

//get user by email
    @GetMapping("/getuserbyemail/{email}")
    public ResponseEntity<UserEntity> getUserByEmail(@PathVariable String email) {
        return adminUserService.findUserByEmail(email);
    }


//banuser by id
    @DeleteMapping("/banuserbyid/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {

        return adminUserService.banUser(id);

    }


////get active users
//    @GetMapping("/activeusers")
//    public List<UserEntity> getAllActiveUsers() {
//        return adminUserService.activeUsers();
//
//    }

//single user analysis results
    @GetMapping("/analysis_results/{userid}")
    public ResponseEntity<List<HairAnalysisEntity>> gethairAnalysisofUser(@PathVariable int userid ) {

        return adminUserService.getAnalysis(userid);

    }
//single users reports
    @GetMapping("/report/{userid}")
    public ResponseEntity<List<ReportEntity>> getReportofUser(@PathVariable int userid ) {

        return adminUserService.getReport(userid);

    }
//single user feed back
    @GetMapping("/feedback/{userid}")
    public ResponseEntity<List<FeedbackEntity>> getFeedbackofUser(@PathVariable int userid ) {

        return adminUserService.getFeedback(userid);

    }



}
////////////////////////////////////////////////////////////////////-*/