package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/header")
    public ResponseEntity<String> authHeader(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return ResponseEntity.ok("Authorization Header: " + authHeader);
    }

    @GetMapping("/param")
    public ResponseEntity<String> authParam(@RequestParam("token") String token) {
        return ResponseEntity.ok("Token Parameter: " + token);
    }

    @GetMapping("/generateToken")
    public ResponseEntity<String> generateToken(@RequestParam("username") String username) {
        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/jwt")
    public ResponseEntity<String> authJwt(@RequestHeader("Authorization") String jwt) {
        String username = jwtUtil.extractUsername(jwt);

        if (jwtUtil.validateToken(jwt, username)) {
            return ResponseEntity.ok("JWT Valid: " + jwt + " for user: " + username);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
        }
    }
}