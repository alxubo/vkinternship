package com.internship.olegchistov.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.annotation.Cacheable;


@RestController
@RequestMapping("/api")
public class ProxyController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String JSON_PLACEHOLDER_URL = "https://jsonplaceholder.typicode.com/";

    @Cacheable(value = "proxyCache", key = "#request.getRequestURI() + #method")
    @RequestMapping(
            value = {"/posts/**", "/users/**", "/albums/**"},
            method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
    )
    public ResponseEntity<?> proxyRequest(
            @RequestBody(required = false) Object requestBody,
            @RequestHeader(required = false) HttpHeaders headers,
            HttpMethod method, HttpServletRequest request) {

        String jsonPlaceholderTargetedApi = request.getRequestURI().replace("/api", "");
        String targetUrl = JSON_PLACEHOLDER_URL.concat(jsonPlaceholderTargetedApi);

        try {
            return restTemplate.exchange(targetUrl, method, new HttpEntity<>(requestBody, headers), byte[].class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body("Proxy error: " + e.getMessage());
        }

    }
}