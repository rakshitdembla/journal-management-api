package com.rakshitdembla.JournalApp.service;
import com.rakshitdembla.JournalApp.entity.JournalEntry;
import com.rakshitdembla.JournalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    // Create Journal Entry
    public JournalEntry saveEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    // Find Entry By ObjectID
    public Optional<JournalEntry> findEntry(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    // Find All Entries
    public List<JournalEntry> findEntries() {
        return journalEntryRepository.findAll();
    }

    // Delete Entry By ObjectID
    public boolean deleteEntry(ObjectId id) {
        journalEntryRepository.deleteById(id);
        return true;
    }
}
