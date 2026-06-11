package com.rakshitdembla.JournalApp.controller;
import com.rakshitdembla.JournalApp.entity.ErrorEntity;
import com.rakshitdembla.JournalApp.entity.JournalEntry;
import com.rakshitdembla.JournalApp.exception.AppException;
import com.rakshitdembla.JournalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    // Create Journal
    @PostMapping()
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry,
                                         Authentication authentication) {
        try {
            String username = authentication.getName();

            return ResponseEntity.status(HttpStatus.CREATED).body(journalEntryService.saveEntry(journalEntry, username));
        }
        catch(AppException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorEntity(e.getMessage()));
        }
    }

    // Find Journal By ObjectId
    @GetMapping("/{id}")
    public ResponseEntity<?> findEntryById(@PathVariable ObjectId id,
                                           Authentication authentication) {
        try {
            String username = authentication.getName();

            return ResponseEntity.status(HttpStatus.OK).body(journalEntryService.findEntry(id, username));
        }
        catch(AppException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorEntity(e.getMessage()));
        }
    }

    // Find All Journals
    @GetMapping("/all")
    public ResponseEntity<?> findAllEntries(Authentication authentication) {
        try {
            String username = authentication.getName();

            List<JournalEntry> journals = journalEntryService.findEntries(username);
            return ResponseEntity.status(HttpStatus.OK).body(journals);
        } catch (AppException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorEntity(e.getMessage()));
        }
    }

    // Delete Journal By ObjectId
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId id,
                                             Authentication authentication) {
        try {
            String username = authentication.getName();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(journalEntryService.deleteEntry(id, username));

        } catch (AppException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorEntity(e.getMessage()));
        }
    }

    // Update Journal By ObjectId
    @PatchMapping("{id}")
    public ResponseEntity<?> updateEntryById(@PathVariable ObjectId id,
                                             @RequestBody JournalEntry newJournal,
                                             Authentication authentication) {
        try {
            String username = authentication.getName();

            newJournal.setId(id);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(journalEntryService.updateEntry(newJournal, username));

        } catch (AppException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorEntity(e.getMessage()));
        }
    }
}
