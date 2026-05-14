package com.rakshitdembla.JournalApp.controller;
import com.rakshitdembla.JournalApp.entity.JournalEntry;
import com.rakshitdembla.JournalApp.repository.UserEntryRepository;
import com.rakshitdembla.JournalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserEntryRepository userEntryRepository;

    // Create Journal
    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String username) {
        return ResponseEntity.status(HttpStatus.CREATED).body(journalEntryService.saveEntry(journalEntry,username));
    }

    // Find Journal By ObjectId
    @GetMapping("/{id}")
    public ResponseEntity<Optional<JournalEntry>> findEntryById(@PathVariable ObjectId id) {
        return ResponseEntity.status(HttpStatus.OK).body(journalEntryService.findEntry(id));
    }

    // Find All Journals
    @GetMapping("/all/{username}")
    public ResponseEntity<List<JournalEntry>> findAllEntries(@PathVariable String username) {
        List<JournalEntry> journals = userEntryRepository.findByUsername(username).get().getJournals();

        return ResponseEntity.status(HttpStatus.OK).body(journals);
    }

    // Delete Journal By ObjectId
    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<String> deleteEntryById(@PathVariable ObjectId id, @PathVariable String username) {
        boolean isDeleted = journalEntryService.deleteEntry(id,username);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Journal entry deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Journal entry not found.");
        }
    }

    // Update Journal By ObjectId
    @PatchMapping("{id}")
    public ResponseEntity<?> updateEntryById(@PathVariable ObjectId id,
                                             @RequestBody JournalEntry newJournal) {

        Optional<JournalEntry> oldJournal = journalEntryService.findEntry(id);

        if (oldJournal.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Journal entry not found.");
        }

        JournalEntry journal = oldJournal.get();

        // Title Validation
        if (!newJournal.getTitle().isEmpty()) {
            journal.setTitle(newJournal.getTitle());
        }

        // Content Validation
        if (newJournal.getContent() != null && !newJournal.getContent().isEmpty()) {
            journal.setContent(newJournal.getContent());
        }

        journalEntryService.saveEntry(journal);
        return ResponseEntity.status(HttpStatus.OK).body(journal);
    }
}
