package fyp.haircareAi.backend.user.controllers;

import fyp.haircareAi.backend.dto.EmailVerificationRequest;
import fyp.haircareAi.backend.user.entities.LoginEntity;
import fyp.haircareAi.backend.user.services.LoginServiceImpl;
import fyp.haircareAi.backend.user.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginServiceImpl loginService;

    @Autowired
    private JwtUtil jwtUtil;

/////////////WITHOUT jwt Token/////////////////
@PostMapping
public ResponseEntity<?> login(@RequestBody LoginEntity user) {
    System.out.println("Login start");

    // Call the service to check the user
    return loginService.checkUser(user);



    // Return 401 Unauthorized with no body if the user credentials are invalid
}
@PostMapping("/forgotPassword")
public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request){
    String email = request.get("email");
    System.out.println(email);
    return loginService.sendCodeForForgotPassword(email);
}

    @PostMapping("/email_verification")
    public Object verifyEmail(@Valid @RequestBody EmailVerificationRequest emailVerificationCode, BindingResult result) {

        return loginService.verifyEmailForForgotPassword(emailVerificationCode,result);


    }

    @PostMapping(value = "/newPassword")
    public ResponseEntity<?> resetPassword(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> request)
            {
                String password = request.get("NewPassword");

        String email=jwtUtil.extractUsername(token.substring(7).trim());
        return loginService.updatePassword(email,password);



    }


}
