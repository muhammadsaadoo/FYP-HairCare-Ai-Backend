package fyp.haircareAi.backend.user.services;

import fyp.haircareAi.backend.admin.adminServices.ImageService;
import fyp.haircareAi.backend.dto.HairAnalysisRequest;
import fyp.haircareAi.backend.user.entities.HairAnalysisEntity;
import fyp.haircareAi.backend.user.entities.ProblemEntity;
import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AnalysisRepo;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import fyp.haircareAi.backend.user.repositories.ProblemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class HairAnalysisService {
    @Autowired
    private AnalysisRepo analysisRepo;
    @Autowired
    private AuthRepo authRepo;
    @Autowired
    private ProblemRepo problemRepo;

    @Value("${spring.image.storage.path}")
    private String storagePath;
    @Autowired
    private ImageService imageService;



//    public ResponseEntity<HairAnalysisEntity> saveHairAnalysis(HairAnalysisEntity analysisResult) {
//        try {
//            HairAnalysisEntity analysis = analysisRepo.save(analysisResult);
//            return ResponseEntity.ok(analysis);
//        } catch (Exception e){
//            return ResponseEntity.internalServerError().build();
//        }
//
//    }

    public  ResponseEntity<?> analyseHair(MultipartFile hairImage,String email) {

        try {
            Optional<UserEntity> user = authRepo.findByEmail(email);
            UserEntity getUser = user.get();

//        send image to ai
            HairAnalysisEntity hair_nalysis = new HairAnalysisEntity();
            hair_nalysis.setUserId(getUser.getUserId());
            hair_nalysis.setProblem("Alopecia");
            hair_nalysis.setImagePath(imageService.insertImage(hairImage));
            hair_nalysis.setRecommendedProduct("Mielle");

            analysisRepo.save(hair_nalysis);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(hair_nalysis);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }



    }

}
