package fyp.haircareAi.backend.user.services;

import fyp.haircareAi.backend.admin.adminServices.ImageService;
import fyp.haircareAi.backend.dto.UpdateUserDto;
import fyp.haircareAi.backend.user.entities.*;
import fyp.haircareAi.backend.user.repositories.*;
import fyp.haircareAi.backend.user.utils.JwtUtil;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
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
import java.util.HashMap;
import java.util.Map;
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
    @Autowired
    private ImageService imageService;

    @Autowired
    private HairImagesRepo hairImagesRepo;
    @Autowired
    private UserPrimaryDetailsRepository userPrimaryDetailsRepository;


    UserEntity user;

    public ResponseEntity<?> insertImage(String email, MultipartFile image) {

        try {
            Optional<UserEntity> oprionaluser = authRepo.findByEmail(email);
            if (oprionaluser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
//            File directory=new File(storagePath);
//            if(!directory.exists()){
//                directory.mkdir();
//            }
//
//            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
//            Path filePath= Paths.get(storagePath,fileName);
//            Files.write(filePath,image.getBytes());
//
//            String contentType = image.getContentType();
//            if (contentType == null) { // If content type is null, use a default
//                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//            }
            String filePath = imageService.insertImage(image);


            UserEntity user = oprionaluser.get();
            String oldImagePath = user.getImagePath();
            if (oldImagePath != null && !oldImagePath.isEmpty()) {
                try {
                    File oldFile = new File(oldImagePath);
                    if (oldFile.exists()) {
                        boolean deleted = oldFile.delete();
                        if (!deleted) {
                            System.err.println("Failed to delete old image: " + oldImagePath);
                        }
                    }
                } catch (Exception e) {
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("error while deleting previous image");
                }
            }
            user.setImagePath(filePath);

            authRepo.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(filePath);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    public ResponseEntity<?> insertReport(String token, ReportEntity report) {

        try {


            String email = jwtUtil.extractUsername(token.substring(7).trim());
            Optional<UserEntity> optionalUser = authRepo.findByEmail(email);
            report.setUserId(optionalUser.get().getUserId());
            reportRepo.save(report);
            emailService.sendEnail(email, "Report Recieved", "we recieved your report we will try ti resolve it as soon as possible Thanks for your Consideration");
            return ResponseEntity.status(HttpStatus.OK).body("report added");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server error");
        }


    }

    public ResponseEntity<?> insertfeedback(String token, FeedbackEntity feedback) {

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

    public ResponseEntity<?> updateUser(UpdateUserDto newUser, String email) {
        try {
            Optional<UserEntity> optionalUser = authRepo.findByEmail(email);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
            }
            UserEntity oldUser = optionalUser.get();
            oldUser.setFirst_name(newUser.getFirst_name());
            oldUser.setLast_name(newUser.getLast_name());
            oldUser.setGender(newUser.getGender());
            oldUser.setAge(newUser.getAge());
            oldUser.setCountry(newUser.getCountry());

            this.user = authRepo.save(oldUser);
            return ResponseEntity.ok(this.user);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("error while update user");
        }


    }


    public ResponseEntity<?> AnalyseImage(String email, MultipartFile image) {

        try {
            Optional<UserEntity> optionalUser = authRepo.findByEmail(email);
            if (optionalUser.isEmpty()) {
                System.out.println("User not found");
                return ResponseEntity.notFound().build();
            }

            String filePath = imageService.insertImage(image);
            System.out.println("Image inserted");

            // Generate AI analysis
            MedicalDiagnosisService medicalDiagnosisService = new MedicalDiagnosisService();
            String fullResult = medicalDiagnosisService.AI(image);
            System.out.println("Result generated");

            // Option 1: Truncate result to fit VARCHAR(255)
            String shortenedResult = truncateResult(fullResult, 250); // Leave some buffer

            // Option 2: Store just the diagnosis name
            // String shortenedResult = "Male Pattern Baldness - Full analysis available";

            UserEntity user = optionalUser.get();
            HairImageEntity hairImageEntity = new HairImageEntity();
            hairImageEntity.setUserId(user.getUserId());
            hairImageEntity.setImagePath(filePath);
            hairImageEntity.setResult(shortenedResult); // Use shortened version

            hairImagesRepo.save(hairImageEntity);

            // Return the full result in response even if we store shortened version
            Map<String, Object> response = new HashMap<>();
            response.put("id", hairImageEntity.getImageId());
            response.put("userId", hairImageEntity.getUserId());
            response.put("imagePath", hairImageEntity.getImagePath());
            response.put("fullResult", fullResult); // Full result in response
            response.put("createdAt", hairImageEntity.getCreatedAt());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Helper method to truncate result
    private String truncateResult(String result, int maxLength) {
        if (result.length() <= maxLength) {
            return result;
        }
        return result.substring(0, maxLength - 3) + "...";
    }

    public ResponseEntity<?> setUserPrimarryDetails(UserPrimaryDetailsEntity user) {

        try {
            userPrimaryDetailsRepository.save(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}









