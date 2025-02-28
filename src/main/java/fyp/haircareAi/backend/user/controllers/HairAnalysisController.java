package fyp.haircareAi.backend.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import fyp.haircareAi.backend.user.entities.HairAnalysisEntity;
import fyp.haircareAi.backend.user.services.HairAnalysisService;

@RestController
@RequestMapping("/hair-analysis")
public class HairAnalysisController {

    private final HairAnalysisService hairAnalysisService;
    private final ObjectMapper objectMapper; // To convert JSON string to Java object

    public HairAnalysisController(HairAnalysisService hairAnalysisService, ObjectMapper objectMapper) {
        this.hairAnalysisService = hairAnalysisService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addAnalysis(
            @RequestPart("analysisResult") String analysisResultJson,
            @RequestPart("imageFile") MultipartFile imageFile
    ) {
        try {
            // Convert JSON string to Java object
            HairAnalysisEntity analysis = objectMapper.readValue(analysisResultJson, HairAnalysisEntity.class);
            return hairAnalysisService.insertAnalysis(analysis, imageFile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        }
    }
}
