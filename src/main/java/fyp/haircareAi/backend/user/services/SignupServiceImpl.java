package fyp.haircareAi.backend.user.services;

import fyp.haircareAi.backend.dto.EmailVerificationRequest;
import fyp.haircareAi.backend.user.entities.BanUserEntity;
import fyp.haircareAi.backend.user.entities.ProductEntity;
import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import fyp.haircareAi.backend.user.repositories.BanUserRepo;
import fyp.haircareAi.backend.user.services.interfaces.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class SignupServiceImpl implements SignUpService {
    @Autowired
    private AuthRepo authRepo;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private BanUserRepo banUserRepo;

    private static final PasswordEncoder passwordencoder=new BCryptPasswordEncoder();


    private UserEntity user=null;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final long EXPIRATION_TIME = 5;



    public ResponseEntity<?> verifyEmail(EmailVerificationRequest email_verification, BindingResult result) {
        // Check for validation errors
        try {
            if (result.hasErrors()) {
                // Return validation errors (you can customize this)
                return (ResponseEntity<?>) result.getFieldErrors().stream()
                        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                        .toList();
            }

            Optional<UserEntity> user=authRepo.findByEmail(email_verification.getEmail());
            if(user.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(email_verification.getEmail()+"    user not found");
            }
            //verify using code
            String storedCode = redisTemplate.opsForValue().get(email_verification.getEmail());
            if (storedCode != null && storedCode.equals(email_verification.getVerificationCode())) {
                // If verified, remove the code from Redis
                redisTemplate.delete(email_verification.getEmail());
                UserEntity dbuser=user.get();
//                dbuser.setVerify(UserEntity.IsVerified.verified);
                dbuser.setVerify(true);
                authRepo.save(dbuser);
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(email_verification.getEmail()+"    verification successfull");
            }
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("verification code is incorrect");



        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Exception occured");
        }
    }
    @Transactional
    public ResponseEntity<?>insertUser(UserEntity user){



            try {
                Optional<BanUserEntity> isban=banUserRepo.findByEmail(user.getEmail());
                if(isban.isPresent()){
//                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();    main
                    return ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body("the Email " + user.getEmail() + " is BAN");
                }
                Optional<UserEntity> user_in_db=authRepo.findByEmail(user.getEmail());


                if(user_in_db.isPresent()){
//                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();    main
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body( user.getEmail()+" already exist ");
                }
                user.setPassword(passwordencoder.encode(user.getPassword()));
                UserEntity is_add = authRepo.save(user);

                // You can add custom logic if needed
                if (is_add != null) {
                    //save verification code in redis cloud
                    Random random = new Random();
                    String verificationCode = String.valueOf(1000 + random.nextInt(9000));
                    if(emailService.sendEnail(user.getEmail(),"HairCare Ai","Your verification code is "+verificationCode)){

                        try {//store verification code in redis cloud
                            redisTemplate.opsForValue().set(user.getEmail(), verificationCode, EXPIRATION_TIME, TimeUnit.MINUTES);
                            return ResponseEntity.ok(user);
                        } catch (Exception e) {
                            System.out.println(e);
                            return ResponseEntity.internalServerError().build();
                        }
                    }
                    return ResponseEntity.internalServerError().build();
                } else {
                    // Handle the case where the user could not be added
                    return ResponseEntity.internalServerError().build();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
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
