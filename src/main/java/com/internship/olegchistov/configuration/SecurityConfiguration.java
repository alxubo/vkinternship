package com.internship.olegchistov.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.internship.olegchistov.models.Role.*;
import static com.internship.olegchistov.models.RolePermissions.*;
import static com.internship.olegchistov.models.RolePermissions.ALBUMS_EDITOR;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(matcherRegistry -> matcherRegistry
                        .requestMatchers("/auth/**").permitAll()

                        .requestMatchers("/api/posts/**").hasAnyRole(ADMIN.name(), POSTS.name())
                        .requestMatchers(GET, "/api/posts/**").hasAnyRole(ADMIN_VIEWER.name(), POSTS_VIEWER.name())
                        .requestMatchers(POST, "/api/posts/**").hasAnyRole(ADMIN_EDITOR.name(), POSTS_EDITOR.name())

                        .requestMatchers("/api/users/**").hasAnyRole(ADMIN.name(), USERS.name())
                        .requestMatchers(GET, "/api/users/**").hasAnyRole(ADMIN_VIEWER.name(), USERS_VIEWER.name())
                        .requestMatchers(POST, "/api/users/**").hasAnyRole(ADMIN_EDITOR.name(), USERS_EDITOR.name())

                        .requestMatchers("/api/albums/**").hasAnyRole(ADMIN.name(), ALBUMS.name())
                        .requestMatchers(GET, "/api/albums/**").hasAnyRole(ADMIN_VIEWER.name(), ALBUMS_VIEWER.name())
                        .requestMatchers(POST, "/api/albums/**").hasAnyRole(ADMIN_EDITOR.name(), ALBUMS_EDITOR.name())
                        .anyRequest().authenticated())


                .sessionManagement(managementConfigurer -> managementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // jwt first

        return httpSecurity.build();
    }

}
