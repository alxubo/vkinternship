package com.internship.olegchistov.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.internship.olegchistov.models.RolePermissions.*;


@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN(
            Set.of(
                    ADMIN_VIEWER,
                    ADMIN_EDITOR,
                    POSTS_VIEWER,
                    POSTS_EDITOR,
                    USERS_VIEWER,
                    USERS_EDITOR,
                    ALBUMS_VIEWER,
                    ALBUMS_EDITOR
            )
    ),
    POSTS(
            Set.of(
                    POSTS_VIEWER,
                    POSTS_EDITOR
            )
    ),
    USERS(
            Set.of(
                    USERS_VIEWER,
                    USERS_EDITOR
            )
    ),
    ALBUMS(
            Set.of(
                    ALBUMS_VIEWER,
                    ALBUMS_EDITOR
            )
    );

    private final Set<RolePermissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
