package fyp.haircareAi.backend.admin.adminControllers;


import fyp.haircareAi.backend.admin.adminServices.AdminFeedbackService;
import fyp.haircareAi.backend.admin.adminServices.AdminReportsService;
import fyp.haircareAi.backend.user.entities.FeedbackEntity;
import fyp.haircareAi.backend.user.entities.ReportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/reports")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ReportsController {

    @Autowired
    private AdminReportsService adminReportsService;


//get all feedbacks
@GetMapping("/allReports")
public ResponseEntity<List<ReportEntity>> getAlreports() {

    return adminReportsService.getAllFeedback();

}



}

