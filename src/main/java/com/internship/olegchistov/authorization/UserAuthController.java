package com.internship.olegchistov.authorization;

import com.internship.olegchistov.pojos.UserLoginRequest;
import com.internship.olegchistov.pojos.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class UserAuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        log.info("User " + userLoginRequest.username() + " is connecting");
        return ResponseEntity.status(200).body(userAuthService.loginUser(userLoginRequest));
    }

    @PostMapping("/register")
    @CrossOrigin
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            return ResponseEntity.status(200).body(userAuthService.registerUser(userRegisterRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }

    }

}
