package fyp.haircareAi.backend.user.services;

import fyp.haircareAi.backend.user.entities.LoginEntity;
import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import fyp.haircareAi.backend.user.services.interfaces.LoginService;
import fyp.haircareAi.backend.user.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthRepo authRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String checkUser(LoginEntity user){
        try {
            // Authenticate the user
            System.out.println("Authenticate....................................");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );


            // Get UserDetails
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Extract roles from authorities
            String roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
            // Update the user status to Active
            // Instead of calling the repository twice, combine the status update and user retrieval
            Optional<UserEntity> dbuser= authRepo.findByEmail(userDetails.getUsername());

            if(dbuser.isPresent()){
                UserEntity userr=dbuser.get();
                userr.setStatus(UserEntity.Status.valueOf("Active"));
                userr.setLastLogin(LocalDateTime.now());// Set the user status to Active
                authRepo.save(userr); // Save the updated user entity with Active status
            }

            // Generate JWT with username and roles
            return  jwtUtil.generateToken(userDetails.getUsername(), roles);



        } catch (AuthenticationException e) {
            log.error("Exception occurred while creating authentication token", e);
            return null;
        }
    }
}


