package com.internship.olegchistov.pojos;

public class UserLoginResponse {
        boolean success;
        String error;
        String token;

    public UserLoginResponse(String error) {
        this.success = false;
        this.error = error;
    }

    public UserLoginResponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }
}
