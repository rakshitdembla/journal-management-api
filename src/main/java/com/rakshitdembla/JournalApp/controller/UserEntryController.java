package com.rakshitdembla.JournalApp.controller;

import com.rakshitdembla.JournalApp.entity.ErrorEntity;
import com.rakshitdembla.JournalApp.entity.UserEntry;
import com.rakshitdembla.JournalApp.exception.AppException;
import com.rakshitdembla.JournalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserEntryController {

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userEntryService.findByUsername(username));
        } catch(AppException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorEntity(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserEntry newUser) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userEntryService.saveUser(newUser));
        } catch(AppException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorEntity(e.getMessage()));
        }
    }

    @PatchMapping("/{user}")
    public  ResponseEntity<?> updateUsername(@PathVariable String user,@RequestBody String username) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userEntryService.updateUsername(user,username));
        } catch(AppException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorEntity(e.getMessage()));
        }
    }
}
