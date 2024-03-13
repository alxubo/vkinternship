package com.internship.olegchistov.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RolePermissions {

    POSTS_VIEWER("posts:read"),
    POSTS_EDITOR("posts:update"),
    USERS_VIEWER("users:read"),
    USERS_EDITOR("users:update"),
    ALBUMS_VIEWER("albums:read"),
    ALBUMS_EDITOR("albums:update"),

    ;

    private final String permission;
}
