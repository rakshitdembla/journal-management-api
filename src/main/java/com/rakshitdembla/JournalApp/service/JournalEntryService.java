package com.rakshitdembla.JournalApp.service;
import com.rakshitdembla.JournalApp.entity.JournalEntry;
import com.rakshitdembla.JournalApp.entity.UserEntry;
import com.rakshitdembla.JournalApp.exception.AppException;
import com.rakshitdembla.JournalApp.repository.JournalEntryRepository;
import com.rakshitdembla.JournalApp.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserEntryRepository userEntryRepository;

    // Save Journal Entry
    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String username) {
        journalEntry.setDate(LocalDateTime.now());

        JournalEntry journal = journalEntryRepository.save(journalEntry);
        UserEntry user = userEntryRepository.findByUsername(username).get();

        user.getJournals().add(journal);
        userEntryRepository.save(user);
        return journalEntry;
    }

    // Save Journal Entry
    public JournalEntry saveEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    // Find Entry By ObjectID
    public Optional<JournalEntry> findEntry(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    // Find All Entries
    public List<JournalEntry> findEntries(String username) {
        return userEntryRepository.findByUsername(username).orElseThrow().getJournals();
    }

    // Delete Entry By ObjectID
    public boolean deleteEntry(ObjectId id, String username) {
        UserEntry user = userEntryRepository.findByUsername(username).get();
        journalEntryRepository.deleteById(id);

        user.getJournals().removeIf(e -> e.getId().equals(id));
        userEntryRepository.save(user);
        return true;
    }
}
