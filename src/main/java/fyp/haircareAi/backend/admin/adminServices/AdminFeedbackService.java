package fyp.haircareAi.backend.admin.adminServices;

import fyp.haircareAi.backend.user.entities.FeedbackEntity;
import fyp.haircareAi.backend.user.repositories.FeedbackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminFeedbackService {

    @Autowired
    private FeedbackRepo feedbackRepo;


    public ResponseEntity<List<FeedbackEntity>> getAllFeedback(){

        try {
            List<FeedbackEntity> list=feedbackRepo.findAll();


            if(list.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
