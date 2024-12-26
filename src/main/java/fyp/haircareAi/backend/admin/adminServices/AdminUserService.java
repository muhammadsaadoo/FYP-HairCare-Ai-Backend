package fyp.haircareAi.backend.admin.adminServices;


import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import fyp.haircareAi.backend.user.services.interfaces.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AdminUserService{
    @Autowired
    private AuthRepo signUpRepo;

    public List<UserEntity> getAllUsers(){
        return signUpRepo.findAll();

    }
    public UserEntity findUserByEmail(String email){
        return signUpRepo.findByEmail(email);

    }
    public void deleteUser(UserEntity user){
        signUpRepo.delete(user);

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


