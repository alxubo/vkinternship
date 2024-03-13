package com.internship.olegchistov.authorization;

import com.internship.olegchistov.configuration.JwtService;
import com.internship.olegchistov.models.Role;
import com.internship.olegchistov.models.User;
import com.internship.olegchistov.pojos.AuthResponse;
import com.internship.olegchistov.pojos.UserLoginRequest;
import com.internship.olegchistov.pojos.UserRegisterRequest;
import com.internship.olegchistov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthResponse loginUser(UserLoginRequest userLoginRequest) {

        // if the authentication fails it will throw an exception
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginRequest.username(),
                userLoginRequest.password()
        ));


        User user = userAuthRepository.findUserByUsername(userLoginRequest.username())
                .orElseThrow(() -> new UsernameNotFoundException("User with such username is not in db"));

        var jwtResponseToken = jwtService.generateJwtTokenFromUserModel(user);

        return AuthResponse.builder()
                .success(jwtResponseToken)
                .build();
    }

    public AuthResponse registerUser(UserRegisterRequest userRegisterRequest) {

        boolean isUsernameInUse = userAuthRepository
                .findUserByUsername(userRegisterRequest.username()).isPresent();

        if (isUsernameInUse) {
            throw new IllegalArgumentException("This username is already in use");
        }

        User user = User.builder()
                .username(userRegisterRequest.username())
                .password(passwordEncoder.encode(userRegisterRequest.password()))
                .role(userRegisterRequest.role())
                .build();

        userAuthRepository.save(user);

        var jwtResponseToken = jwtService.generateJwtTokenFromUserModel(user);

        return AuthResponse.builder()
                .success(jwtResponseToken)
                .build();
    }
}
