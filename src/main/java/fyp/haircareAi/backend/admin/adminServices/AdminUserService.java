package fyp.haircareAi.backend.admin.adminServices;


import fyp.haircareAi.backend.admin.cache.UserCache;
import fyp.haircareAi.backend.user.entities.BanUserEntity;
import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import fyp.haircareAi.backend.user.repositories.BanUserRepo;
import fyp.haircareAi.backend.user.services.interfaces.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AuthRepo signUpRepo;


    @Autowired
    private BanUserRepo banUserRepo;

    @Autowired
    private UserCache userCache;

    public List<UserEntity> getAllUsers(){
        try {

            userCache.getAllUsers();
            return userCache.getUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public List<UserEntity> getAllAdmins(){
        return signUpRepo.findByRole(UserEntity.Role.ADMIN);

    }
    public UserEntity findUserByEmail(String email){
        Optional<UserEntity> dbuser= signUpRepo.findByEmail(email);

        return dbuser.orElse(null);

    }
    @Transactional
    public boolean deleteUser(UserEntity user){
        try {

            signUpRepo.delete(user);
            BanUserEntity banUser=new BanUserEntity();
            banUser.setEmail(user.getEmail());
           banUserRepo.save(banUser);


            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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


