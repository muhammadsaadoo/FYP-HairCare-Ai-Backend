package fyp.haircareAi.backend.user.services;

import fyp.haircareAi.backend.user.entities.ProductEntity;
import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import fyp.haircareAi.backend.user.services.interfaces.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;

@Service
public class SignupServiceImpl implements SignUpService {
    @Autowired
    private AuthRepo authRepo;
    @Autowired
    private EmailServiceImpl emailService;

    private static final PasswordEncoder passwordencoder=new BCryptPasswordEncoder();
    private int verificationCode=0;

    private UserEntity user=null;

    @Override
    public Object insertUser(String verificationCode){
        int number = Integer.parseInt(verificationCode);

        if(number==this.verificationCode && user!=null) {
            try {
                user.setPassword(passwordencoder.encode(user.getPassword()));
                user.setRole(UserEntity.Role.valueOf("USER"));

                UserEntity is_add = authRepo.save(user);


                // You can add custom logic if needed
                if (is_add != null) {
                    user= null;

                    this.verificationCode=0;
                    return is_add;
                } else {
                    // Handle the case where the user could not be added
                    return "Failed to register user";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        else{
            return "wrong verification code";
        }

    }

    public Object verifyEmail(UserEntity newUser, BindingResult result) {
        // Check for validation errors
        try {
            if (result.hasErrors()) {
                // Return validation errors (you can customize this)
                return result.getFieldErrors().stream()
                        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                        .toList();
            }
            //encrypt password


            // If no validation errors, proceed with saving the user
            Random random = new Random();

            verificationCode= 1000 + random.nextInt(9000);


            if( emailService.sendEnail(newUser.getEmail(),"HairCare Ai","Your verification code is "+verificationCode)){
                user=newUser;

                return "verification code sent to your gmail";
            }
            else{
                return "enter correct gmail";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
    public Object insertUser(UserEntity user){



            try {
                user.setPassword(passwordencoder.encode(user.getPassword()));


                UserEntity is_add = authRepo.save(user);


                // You can add custom logic if needed
                if (is_add != null) {
                    return is_add;
                } else {
                    // Handle the case where the user could not be added
                    return "Failed to register user";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }



    }
//    public UserEntity insertUser(UserEntity user, MultipartFile imageFile) throws IOException {
//        user.setImageName(imageFile.getOriginalFilename());
//        user.setImageType(imageFile.getContentType());
//        user.setImageData(imageFile.getBytes());
//        user.setPassword(passwordencoder.encode(user.getPassword()));
//
//
//        return authRepo.save(user);
//
//
//    }
}
