package com.internship.olegchistov.authorization;

import com.internship.olegchistov.configuration.JwtService;
import com.internship.olegchistov.models.User;
import com.internship.olegchistov.pojos.ValidateResponse;
import com.internship.olegchistov.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userAuthRepository;

    public List<User> getAllUsers() {
        return userAuthRepository.findAll();
    }

    public void deleteUserById(ObjectId id) {
        userAuthRepository.deleteById(id);
    }
}
