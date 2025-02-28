package fyp.haircareAi.backend.user.services;

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



//    public ResponseEntity<HairAnalysisEntity> saveHairAnalysis(HairAnalysisEntity analysisResult) {
//        try {
//            HairAnalysisEntity analysis = analysisRepo.save(analysisResult);
//            return ResponseEntity.ok(analysis);
//        } catch (Exception e){
//            return ResponseEntity.internalServerError().build();
//        }
//
//    }

    public ResponseEntity<byte[]> insertAnalysis(HairAnalysisEntity analysisResult, MultipartFile image){

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


            analysisResult.setImagePath(filePath.toString());

            analysisRepo.save(analysisResult);


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
}
