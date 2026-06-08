package com.rakshitdembla.JournalApp.controller;

import com.rakshitdembla.JournalApp.entity.ErrorEntity;
import com.rakshitdembla.JournalApp.entity.UserEntry;
import com.rakshitdembla.JournalApp.exception.AppException;
import com.rakshitdembla.JournalApp.repository.UserEntryRepository;
import com.rakshitdembla.JournalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK).body("Health - OK");
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserEntry newUser) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userEntryService.saveUser(newUser));
        } catch(AppException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorEntity(e.getMessage()));
        }
    }
}
