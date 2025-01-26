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
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class LoginController {
    @Autowired
    private LoginServiceImpl loginService;

/////////////WITHOUT jwt Token/////////////////
@PostMapping
public ResponseEntity<?> login(@RequestBody LoginEntity user) {
    System.out.println("Login start");

    // Call the service to check the user
    String token = loginService.checkUser(user);

    if (token != null) {
        // Wrap the token in a JSON structure
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    // Return 401 Unauthorized with no body if the user credentials are invalid
    return ResponseEntity.notFound().build();
}


}
