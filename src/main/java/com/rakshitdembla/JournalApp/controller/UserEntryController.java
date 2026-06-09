package com.rakshitdembla.JournalApp.controller;

import com.rakshitdembla.JournalApp.entity.ErrorEntity;
import com.rakshitdembla.JournalApp.exception.AppException;
import com.rakshitdembla.JournalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserEntryController {

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping()
    public ResponseEntity<?> getUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            return ResponseEntity.status(HttpStatus.OK).body(userEntryService.findByUsername(username));
        } catch (AppException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorEntity(e.getMessage()));
        }
    }

    @PatchMapping("/{username}")
    public ResponseEntity<?> updateUsername(@PathVariable String username) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = auth.getName();

            return ResponseEntity.status(HttpStatus.OK).body(userEntryService.updateUsername(user, username));
        } catch (AppException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorEntity(e.getMessage()));
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userEntryService.deleteUserByUsername(username));
        } catch (AppException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorEntity(e.getMessage()));
        }
    }
}
