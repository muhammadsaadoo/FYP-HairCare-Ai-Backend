package fyp.haircareAi.backend.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fyp.haircareAi.backend.user.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import fyp.haircareAi.backend.user.entities.HairAnalysisEntity;
import fyp.haircareAi.backend.user.services.HairAnalysisService;

@RestController
@RequestMapping("/hair-analysis")
public class HairAnalysisController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HairAnalysisService hairAnalysisService;

    @PostMapping(value = "/analyseHair", consumes = {"multipart/form-data"})
    public ResponseEntity<?> analyseImage(
            @RequestHeader("Authorization") String token,
            @RequestPart("imageFile") MultipartFile imageFile){

        String emil=jwtUtil.extractUsername(token.substring(7).trim());
        return hairAnalysisService.analyseHair(imageFile,emil);


    }
}
