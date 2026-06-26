package com.rakshitdembla.JournalApp.scheduler;

import com.rakshitdembla.JournalApp.entity.JournalEntry;
import com.rakshitdembla.JournalApp.entity.UserEntry;
import com.rakshitdembla.JournalApp.enums.Sentiment;
import com.rakshitdembla.JournalApp.repository.UserEntryImplRepository;
import com.rakshitdembla.JournalApp.repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class UserScheduler {

    @Autowired
    private UserEntryImplRepository userEntryImplRepository;

    @Autowired
    private UserEntryRepository userEntryRepository;

    @Scheduled(fixedRate = 60000) // every 60 seconds
    public void refreshPrevWeekSentiment() {

        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);

        List<UserEntry> users = userEntryImplRepository.getSAusers();

        for (UserEntry user : users) {

            EnumMap<Sentiment, Integer> counts = new EnumMap<>(Sentiment.class);

            for (JournalEntry journal : user.getJournals()) {

                if (journal.getDate() == null ||
                        journal.getDate().isBefore(oneWeekAgo) ||
                        journal.getSentiment() == null) {
                    continue;
                }

                counts.merge(journal.getSentiment(), 1, Integer::sum);
            }

            Sentiment majoritySentiment = counts.entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            if (majoritySentiment != null) {
                user.setPrevWeekSentiment(majoritySentiment);
                userEntryRepository.save(user);
            }
        }
    }
}
