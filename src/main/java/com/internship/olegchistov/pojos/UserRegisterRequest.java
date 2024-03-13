package com.internship.olegchistov.pojos;

import com.internship.olegchistov.models.Role;

public record UserRegisterRequest(
        String username,
        String password,
        Role role) {

}
