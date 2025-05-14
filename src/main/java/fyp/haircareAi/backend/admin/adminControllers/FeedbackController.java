package fyp.haircareAi.backend.admin.adminControllers;


import fyp.haircareAi.backend.admin.adminServices.AdminFeedbackService;
import fyp.haircareAi.backend.admin.adminServices.AssignRolesService;
import fyp.haircareAi.backend.user.entities.FeedbackEntity;
import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.services.interfaces.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/feedback")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class FeedbackController {

    @Autowired
    private AdminFeedbackService adminFeedbackService;


//get all feedbacks
@GetMapping("/allFeedbacks")
public ResponseEntity<List<FeedbackEntity>> getAlUFeedbacks() {

    return adminFeedbackService.getAllFeedback();

}



}

