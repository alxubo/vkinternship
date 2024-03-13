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

    @GetMapping("/list")
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable ObjectId id) {
        userService.deleteUserById(id);
    }


}

