package com.rakshitdembla.JournalApp.service;
import com.rakshitdembla.JournalApp.entity.JournalEntry;
import com.rakshitdembla.JournalApp.entity.UserEntry;
import com.rakshitdembla.JournalApp.exception.AppException;
import com.rakshitdembla.JournalApp.repository.JournalEntryRepository;
import com.rakshitdembla.JournalApp.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserEntryRepository userEntryRepository;

    @Autowired
    private UserEntryService userEntryService;

    // Save Journal Entry
    public JournalEntry saveEntry(JournalEntry journalEntry, String username) {
        try {
            return saveJournal(journalEntry,username);
        }
        catch(Exception e) {
            throw new AppException(500,e.getMessage());
        }
    }

    @Transactional
    private JournalEntry saveJournal(JournalEntry journalEntry, String username) {
        journalEntry.setDate(LocalDateTime.now());

        JournalEntry journal = journalEntryRepository.save(journalEntry);
        UserEntry user = userEntryRepository.findByUsername(username).get();

        user.getJournals().add(journal);
        userEntryRepository.save(user);
        return journalEntry;
    }

    // Update Journal Entry
    public JournalEntry updateEntry(JournalEntry journalEntry,String username) {
        try {
            UserEntry user = userEntryService.findByUsername(username);
            JournalEntry journal = null;

            for (JournalEntry j : user.getJournals()) {
                if (j.getId().equals(journalEntry.getId())) {
                    journal = j;
                    break;
                }
            }

            if (journal == null) throw new AppException(404,"Journal not found");
            return journalEntryRepository.save(journalEntry);
        }
        catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(500,e.getMessage());
        }
    }

    // Find Entry By ObjectID
    public JournalEntry findEntry(ObjectId id,String username) {
        UserEntry user = userEntryService.findByUsername(username);

        try {
            for (JournalEntry j : user.getJournals()) {
                if (j.getId().equals(id)) return j;
            }

            throw new AppException(404,"Journal not found");
        }
        catch (AppException e) {
            throw e;
        }
        catch (Exception e) {
            throw new AppException(500,e.getMessage());
        }
    }

    // Find All Entries
    public List<JournalEntry> findEntries(String username) {
        try {
            UserEntry user = userEntryService.findByUsername(username);
            return user.getJournals();
        } catch (Exception e) {
            throw new AppException(500,e.getMessage());
        }
    }

    // Delete Entry By ObjectID
    public boolean deleteEntry(ObjectId id, String username) {
        try {
            boolean isDeleted = deleteJournal(id,username);

            if (isDeleted) return true;
            throw new AppException(404,"Journal not found");
        }
        catch (AppException e) {
            throw  e;
        }
        catch (Exception e) {
            throw new AppException(500,e.getMessage());
        }
    }

    @Transactional
    private boolean deleteJournal(ObjectId id, String username) {
        UserEntry user = userEntryRepository.findByUsername(username).get();
        journalEntryRepository.deleteById(id);

        int beforeSize = user.getJournals().size();

        user.getJournals().removeIf(e -> e.getId().equals(id));
        userEntryRepository.save(user);

        int afterSize = user.getJournals().size();

        return beforeSize != afterSize;
    }
}
