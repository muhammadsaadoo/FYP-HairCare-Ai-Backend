package fyp.haircareAi.backend.user.controllers;


import fyp.haircareAi.backend.user.services.GoogleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/google")
public class GoogleAuthController {

      @Autowired
    private GoogleAuthService googleAuthService;

    @GetMapping("/callback")
    public ResponseEntity<?> googleauth(@RequestParam String code){
        System.out.println(code);
        return googleAuthService.handleGoogleCallback(code);

    }


}
