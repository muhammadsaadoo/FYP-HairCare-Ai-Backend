package fyp.haircareAi.backend.user.controllers;

import fyp.haircareAi.backend.dto.EmailVerificationRequest;
import fyp.haircareAi.backend.user.entities.ProductEntity;
import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.services.SignupServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    private SignupServiceImpl signupService;

    @PostMapping("/email_verification")
    public Object verifyEmail(@Valid @RequestBody EmailVerificationRequest email_verificationCode, BindingResult result) {

          return signupService.verifyEmail(email_verificationCode,result);


    }
    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserEntity user){
        System.out.println(user.toString());
        return signupService.insertUser(user);

    }

//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> addProduct(
//            @RequestPart("user") UserEntity user,
//            @RequestPart("imageFile") MultipartFile imageFile
//    ) {
//
//        try {
//            UserEntity newUser = signupService.insertUser(user, imageFile);
//            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
//        } catch (IOException e) {
//            return ResponseEntity.internalServerError().build(); // Added .build()
//        }
//    }

    ////////////////////////////DUMMY?????????????????????????????????????



}
