package fyp.haircareAi.backend.user.controllers;

import fyp.haircareAi.backend.user.entities.LoginEntity;
import fyp.haircareAi.backend.user.services.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginServiceImpl loginService;

/////////////WITHOUT jwt Token/////////////////
    @PostMapping
    public String login(@RequestBody LoginEntity user){
        System.out.println("login start");
        return loginService.checkUser(user);

    }


}
