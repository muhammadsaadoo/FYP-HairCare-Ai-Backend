package fyp.haircareAi.backend.user.controllers;

import fyp.haircareAi.backend.user.entities.LoginEntity;
import fyp.haircareAi.backend.user.services.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginServiceImpl loginService;

/////////////WITHOUT jwt Token/////////////////
@PostMapping
public ResponseEntity<?> login(@RequestBody LoginEntity user) {
    System.out.println("Login start");

    // Call the service to check the user
    return loginService.checkUser(user);



    // Return 401 Unauthorized with no body if the user credentials are invalid
}


}
