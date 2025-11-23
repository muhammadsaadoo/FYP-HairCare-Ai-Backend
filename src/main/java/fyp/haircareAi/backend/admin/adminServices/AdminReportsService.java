package fyp.haircareAi.backend.admin.adminServices;

import fyp.haircareAi.backend.user.entities.FeedbackEntity;
import fyp.haircareAi.backend.user.entities.ReportEntity;
import fyp.haircareAi.backend.user.repositories.FeedbackRepo;
import fyp.haircareAi.backend.user.repositories.ReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminReportsService {

    @Autowired
    private ReportRepo reportRepo;


    public ResponseEntity<List<ReportEntity>> getAllReports(){

        try {
            List<ReportEntity> list=reportRepo.findAll();

            if(list.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
