package fyp.haircareAi.backend.admin.adminServices;


import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import fyp.haircareAi.backend.user.services.interfaces.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AdminUserService{
    @Autowired
    private AuthRepo signUpRepo;

    public List<UserEntity> getAllUsers(){
        return signUpRepo.findAll();

    }
    public List<UserEntity> getAllAdmins(){
        return signUpRepo.findByRole(UserEntity.Role.ADMIN);

    }
    public UserEntity findUserByEmail(String email){
        return signUpRepo.findByEmail(email);

    }
    public void deleteUser(UserEntity user){
        signUpRepo.delete(user);

    }
    public List<UserEntity> activeUsers() {
        List<UserEntity> allUsers = getAllUsers(); // Fetch all users
        List<UserEntity> activeUsers = new ArrayList<>(); // Initialize the active users list

        LocalDateTime currentTime = LocalDateTime.now(); // Current time

        for (UserEntity user : allUsers) {
            LocalDateTime lastActiveTime = user.getLastLogin(); // Last login timestamp

            if (lastActiveTime != null && Duration.between(lastActiveTime, currentTime).toHours() > 24) {
                // If the user has been inactive for more than 24 hours
                user.setStatus(UserEntity.Status.valueOf("InActive"));
            } else {
                // If the user is active
                user.setStatus(UserEntity.Status.valueOf("Active"));
                activeUsers.add(user); // Add to the active users list
            }

            signUpRepo.save(user); // Persist changes in the database
        }

        return activeUsers; // Return the list of active users
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


