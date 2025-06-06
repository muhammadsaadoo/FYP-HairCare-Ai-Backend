package fyp.haircareAi.backend.user.services;

import fyp.haircareAi.backend.user.entities.FeedbackEntity;
import fyp.haircareAi.backend.user.entities.ProductEntity;
import fyp.haircareAi.backend.user.entities.ReportEntity;
import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import fyp.haircareAi.backend.user.repositories.FeedbackRepo;
import fyp.haircareAi.backend.user.repositories.ReportRepo;
import fyp.haircareAi.backend.user.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
@Service
public class UserService {

    @Value("${spring.image.storage.path}")
    private String storagePath;

    @Autowired
    private AuthRepo authRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ReportRepo reportRepo;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private FeedbackRepo feedbackRepo;

    public ResponseEntity<byte[]> insertImage(String email, MultipartFile image){
        Optional<UserEntity> oprionaluser=authRepo.findByEmail(email);
        if(oprionaluser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        try {
            File directory=new File(storagePath);
            if(!directory.exists()){
                directory.mkdir();
            }

            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path filePath= Paths.get(storagePath,fileName);
            Files.write(filePath,image.getBytes());

            String contentType = image.getContentType();
            if (contentType == null) { // If content type is null, use a default
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            UserEntity user=oprionaluser.get();
            user.setImagePath(filePath.toString());

            authRepo.save(user);


            byte[] imageBytes = Files.readAllBytes(filePath);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.parseMediaType(contentType)) // Adjust this to match the actual image type
                    .body(imageBytes);




        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    public ResponseEntity<?> insertReport(String token, ReportEntity report){

        try {


            String email = jwtUtil.extractUsername(token.substring(7).trim());
            Optional<UserEntity> optionalUser = authRepo.findByEmail(email);
            report.setUserId(optionalUser.get().getUserId());
            reportRepo.save(report);
            emailService.sendEnail(email,"Report Recieved","we recieved your report we will try ti resolve it as soon as possible Thanks for your Consideration");
            return ResponseEntity.status(HttpStatus.OK).body("report added");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server error");
        }


    }
    public ResponseEntity<?> insertfeedback(String token, FeedbackEntity feedback){

        try {


            String email = jwtUtil.extractUsername(token.substring(7).trim());
            Optional<UserEntity> optionalUser = authRepo.findByEmail(email);
            feedback.setUserId(optionalUser.get().getUserId());
            feedbackRepo.save(feedback);
            return ResponseEntity.status(HttpStatus.OK).body("feedback added");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server error");
        }


    }

//    public ResponseEntity<byte[]> getUserImage(String email) {
//        try {
//            Optional<UserEntity> user = authRepo.findByEmail(email);
//
//
//            if (user.isPresent() && user.get().getImageData() != null) {
//                return ResponseEntity.ok()
//                        .contentType(MediaType.valueOf(user.get().getImageType()))
//                        .body(user.get().getImageData());
//            }
//            else{
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().build();
//        }
//
//    }
}
