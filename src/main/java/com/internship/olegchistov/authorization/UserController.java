package com.internship.olegchistov.authorization;

import com.internship.olegchistov.models.User;
import com.internship.olegchistov.pojos.ValidateResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/validate")
    @CrossOrigin
    public ResponseEntity<ValidateResponse> validateUser(@NonNull HttpServletRequest request) {
        try {
            final String authHeader = request.getHeader("Authorization");

            return ResponseEntity.status(200).body(userService.validateUser(authHeader));
        } catch (IllegalArgumentException e) {
            log.warn("Endpoint users/validate error: " + e.getMessage());
            return ResponseEntity.status(404).body(null);
        }

    }

    @GetMapping("/list")
    @CrossOrigin
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @DeleteMapping("/delete/{id}")
    @CrossOrigin
    public void deleteUser(@PathVariable ObjectId id) {
        userService.deleteUserById(id);
    }


}

