package com.rakshitdembla.JournalApp.repository;
import com.rakshitdembla.JournalApp.entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserEntryRepository extends MongoRepository<UserEntry, ObjectId> {
    Optional<UserEntry> findByUsername(String username);
}
