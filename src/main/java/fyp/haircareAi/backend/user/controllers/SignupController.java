package fyp.haircareAi.backend.user.controllers;

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

//    @PostMapping
//    public Object verifyEmail(@Valid @RequestBody UserEntity newUser, BindingResult result) {
//
//          return signupService.verifyEmail(newUser,result);
//
//
//    }
    @PostMapping
    public Object verify(@RequestBody UserEntity user){
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
