package fyp.haircareAi.backend.admin.adminServices;


import fyp.haircareAi.backend.admin.cache.UserCache;
import fyp.haircareAi.backend.user.entities.*;
import fyp.haircareAi.backend.user.repositories.*;
import fyp.haircareAi.backend.user.services.interfaces.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdminUserService{
    @Autowired
    private AuthRepo authRepo;


    @Autowired
    private BanUserRepo banUserRepo;

    @Autowired
    private UserCache userCache;
    @Autowired
    private AnalysisRepo analysisRepo;

    @Autowired
    private ReportRepo reportRepo;

    @Autowired
    private FeedbackRepo feedbackRepo;

    public ResponseEntity<List<UserEntity>> getAllUsers(){
        try {

            userCache.getAllUsers();
            return ResponseEntity.ok(userCache.getUsers());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public ResponseEntity<List<UserEntity>> getAllAdmins(){
        return ResponseEntity.ok(authRepo.findByRole(UserEntity.Role.ADMIN));

    }
    public UserEntity findUserByEmail(String email){
        Optional<UserEntity> dbuser= authRepo.findByEmail(email);

        return dbuser.orElse(null);

    }
//    public ResponseEntity<?> deleteUser(String email){
//        try{
//            authRepo.(email);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Transactional
    public ResponseEntity<?> banUser(int id){
        try {
            Optional<UserEntity> optionalUser=authRepo.findById(id);

            if(optionalUser.isPresent()) {
                authRepo.delete(optionalUser.get());
                BanUserEntity banUser = new BanUserEntity();
                banUser.setEmail(optionalUser.get().getEmail());
                banUserRepo.save(banUser);


                return ResponseEntity.status(HttpStatus.OK).body("User Deleted");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
        }

    }


    public List<UserEntity> activeUsers() {
        // Retrieve all users
        List<UserEntity> users = getAllUsers();
        List<UserEntity> activeUsers = new ArrayList<>();
        LocalDateTime currentTime = LocalDateTime.now();

        for (UserEntity user : users) {
            LocalDateTime lastActiveTime = user.getLastLogin(); // Get the last login timestamp

            // Check if the user has been active within the last 24 hours or is explicitly marked as "Active"
//            if (user.getStatus().equals(UserEntity.Status.Active) &&
//                    (lastActiveTime != null && Duration.between(lastActiveTime, currentTime).toHours() <= 24)) {
//                activeUsers.add(user); // Add to active users list
//            } else {
//                // Mark user as inactive if not active within 24 hours or not explicitly "Active"
//                user.setStatus(UserEntity.Status.InActive);
//            }
            if(user.getStatus().equals(UserEntity.Status.Active)){
                activeUsers.add(user);
            }

            // Persist changes (status update) in the database
//            signUpRepo.save(user);
        }

        // Return the list of active users
        return activeUsers;
    }


    public void userDashboard(){

    }

    public ResponseEntity<List<HairAnalysisEntity>> getAnalysis(int userId){
        try {
            List<HairAnalysisEntity> listofanalysis=analysisRepo.findByUserId(userId);
            if(listofanalysis.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(listofanalysis);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<ReportEntity>> getReport(int userId){
        try {
            List<ReportEntity> listofReports=reportRepo.findByUserId(userId);
            if(listofReports.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(listofReports);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<FeedbackEntity>> getFeedback(int userId){
        try {
            List<FeedbackEntity> listoffeedback=feedbackRepo.findByUserId(userId);
            if(listoffeedback.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(listoffeedback);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }




//    @Scheduled(cron = "0 0/2 * * * ?")
//    public void sendEmails(){
//        String subject="cart products";
//        String body="you cart some produvts to buy";
//      try{
//        List<String> emails= usersWhoCartProducts();
//        for (String email : emails) {
//            try {
//                emailService.sendEnail(email, subject, body);
//                log.info("email  sent on : {}", email);
//            } catch (Exception e) {
//                log.error("emial not sent to : {}",email);
//                throw new RuntimeException(e);
//            }
//
//
//
//
//             // Assuming sendEmailToUser is a method that sends an email
//        }
//
//    } catch (Exception e) {
//        throw new RuntimeException(e);
//    }

}


