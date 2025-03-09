package fyp.haircareAi.backend.user.controllers;

import fyp.haircareAi.backend.user.entities.ReportEntity;
import fyp.haircareAi.backend.user.services.UserService;
import fyp.haircareAi.backend.user.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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



    // API to store an image



    @PostMapping("/Uploadprofileimage")
    public ResponseEntity<byte[]> getUserImage(
            @RequestHeader("Authorization") String token,
            @RequestParam("image") MultipartFile image
    ){

        String emil=jwtUtil.extractUsername(token.substring(7).trim());
        System.out.println(emil);
        return userService.insertImage(emil,image);


    }

    @PostMapping("/report")
    public ResponseEntity<?> addreport(
            @RequestBody ReportEntity report,
            @RequestHeader("Authorization") String token
    ){


        return  userService.insertReport(token,report);
    }


}
