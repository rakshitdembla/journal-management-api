package com.rakshitdembla.JournalApp.controller;
import com.rakshitdembla.JournalApp.entity.ErrorEntity;
import com.rakshitdembla.JournalApp.entity.LoginRequest;
import com.rakshitdembla.JournalApp.entity.UserEntry;
import com.rakshitdembla.JournalApp.exception.AppException;
import com.rakshitdembla.JournalApp.service.JwtService;
import com.rakshitdembla.JournalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserEntryService userEntryService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserEntry newUser) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userEntryService.saveUser(newUser));
        } catch(AppException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorEntity(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( loginRequest.getUsername(),
                    loginRequest.getPassword()));

            UserDetails userDetails =
                    (UserDetails) authentication.getPrincipal();

            Map<String, Object> claims = new HashMap<>();

            String token =
                    jwtService.generateToken(userDetails, claims);

            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorEntity(e.getMessage()));
        }
    }
}
