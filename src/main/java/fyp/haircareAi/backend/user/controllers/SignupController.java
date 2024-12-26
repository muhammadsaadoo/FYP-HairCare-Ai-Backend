package fyp.haircareAi.backend.user.controllers;

import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.services.SignupServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    ////////////////////////////DUMMY?????????????????????????????????????



}
