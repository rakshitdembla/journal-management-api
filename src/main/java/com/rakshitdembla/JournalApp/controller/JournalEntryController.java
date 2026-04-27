package com.rakshitdembla.JournalApp.controller;
import com.rakshitdembla.JournalApp.entity.JournalEntry;
import com.rakshitdembla.JournalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    // Create Journal
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());

        journalEntry.setDate(LocalDateTime.now());
        JournalEntry journal = journalEntryService.saveEntry(journalEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body(journal);
    }

    // Find Journal By ObjectId
    @GetMapping("{id}")
    public ResponseEntity<Optional<JournalEntry>> findEntryById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journal = journalEntryService.findEntry(id);

        if (journal.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(journal);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(journal);
        }
    }

    // Find All Journals
    @GetMapping()
    public ResponseEntity<List<JournalEntry>> findAllEntries() {
        List<JournalEntry> journals = journalEntryService.findEntries();

        if (journals.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(journals);
        }

        return ResponseEntity.status(HttpStatus.OK).body(journals);
    }

    // Delete Journal By ObjectId
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEntryById(@PathVariable ObjectId id) {
        boolean isDeleted = journalEntryService.deleteEntry(id);

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
        if (newJournal.getTitle() != null && !newJournal.getTitle().isEmpty()) {
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
