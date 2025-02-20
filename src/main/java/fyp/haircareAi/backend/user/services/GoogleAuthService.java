package fyp.haircareAi.backend.user.services;

import fyp.haircareAi.backend.user.entities.UserEntity;
import fyp.haircareAi.backend.user.repositories.AuthRepo;
import fyp.haircareAi.backend.user.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
public class GoogleAuthService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthRepo authRepo;

    @Autowired
    private JwtUtil jwtUtil;


    private UserEntity user=new UserEntity();

    private String jwtToken;

    public ResponseEntity<?> handleGoogleCallback(String code){

        try {
            // Exchange auth code for tokens
            String tokenEndpoint = "https://oauth2.googleapis.com/token";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", "https://developers.google.com/oauthplayground");
            params.add("grant_type", "authorization_code");
            //https://console.cloud.google.com/auth/clients?inv=1&invt=AbpkIw&project=haircareai-450917
//https://developers.google.com/oauthplayground/?code=4%2F0ASVgi3KNnMygbP6u9CPXubZCESayUzMtC12R8a0FP1OWg5LwYjpC9hbbjrax646PftBLEw&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=consent


//            call the api
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndpoint, request, Map.class);
            System.out.println(tokenResponse);

            String idToken = (String) tokenResponse.getBody().get("id_token");
            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);

            if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> userInfo = userInfoResponse.getBody();
                System.out.println(userInfo);
                String email = (String) userInfo.get("email");
                Optional<UserEntity> dbUser=authRepo.findByEmail(email);

                if(dbUser.isPresent()){
                    user=dbUser.get();
                }
                else {
                    user.setEmail(email);
                    user.setFirst_name(email.split("@")[0]);
                    user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    user.setRole(UserEntity.Role.USER);
                    user.setStatus(UserEntity.Status.Active);
                    user.setLastLogin(LocalDateTime.now());
                    user.setLast_name("undefine");
//                    user.setVerify(UserEntity.IsVerified.verified);
                    user.setVerify(true);


                    authRepo.save(user);
                }
                jwtToken = jwtUtil.generateToken(email, String.valueOf(user.getRole()));
                return ResponseEntity.ok(new AuthResponse(jwtToken, user));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (Exception e) {
            log.error("Exception occurred while handleGoogleCallback ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

/*

https://accounts.google.com/o/oauth2/auth?
client_id=YOUR_CLIENT_ID
    &redirect_uri=YOUR_REDIRECT_URI
    &response_type=code
    &scope=email profile
    &access_type=offline
    &prompt=consent

*/