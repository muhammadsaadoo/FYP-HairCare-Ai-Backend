package fyp.haircareAi.backend.user.controllers;

import fyp.haircareAi.backend.user.entities.FeedbackEntity;
import fyp.haircareAi.backend.user.entities.ReportEntity;
import fyp.haircareAi.backend.user.services.HairAnalysisService;
import fyp.haircareAi.backend.user.services.UserDetailServiceImpl;
import fyp.haircareAi.backend.user.services.UserService;
import fyp.haircareAi.backend.user.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/user")
public class UserDetailsController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private HairAnalysisService hairAnalysisService;



    // API to store an image





    @PostMapping("/report")
    public ResponseEntity<?> addreport(
            @RequestBody ReportEntity report,
            @RequestHeader("Authorization") String token
    ){


        return  userService.insertReport(token,report);
    }

    @PostMapping("/feedback")
    public ResponseEntity<?> addfeedback(
            @RequestBody FeedbackEntity feedback,
            @RequestHeader("Authorization") String token
    ){


        return  userService.insertfeedback(token,feedback);
    }


}
