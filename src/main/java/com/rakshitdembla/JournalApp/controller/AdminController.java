package com.rakshitdembla.JournalApp.controller;

import com.rakshitdembla.JournalApp.cache.AppConfigCache;
import com.rakshitdembla.JournalApp.entity.ErrorEntity;
import com.rakshitdembla.JournalApp.exception.AppException;
import com.rakshitdembla.JournalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserEntryService userEntryService;

    @Autowired
    private AppConfigCache appConfigCache;

    // Get all users list
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userEntryService.getAllUsers());
        }
        catch (AppException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorEntity(e.getMessage()));
        }
    }

    // Promote user to admin
    @PostMapping("/make-admin/{username}")
    public ResponseEntity<?> makeUserAdmin(@PathVariable String username) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userEntryService.promoteToAdmin(username));
        }
        catch (AppException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorEntity(e.getMessage()));
        }
    }

    // Reload config cache
    @PostMapping("clear-cache")
    public ResponseEntity<?> clearCache() {
            appConfigCache.init();
            return ResponseEntity.status(HttpStatus.OK).body("Refreshed");
    }
}
