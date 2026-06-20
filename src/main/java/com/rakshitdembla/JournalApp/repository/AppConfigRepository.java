package com.rakshitdembla.JournalApp.repository;

import com.rakshitdembla.JournalApp.entity.ConfigEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppConfigRepository extends MongoRepository<ConfigEntity, ObjectId> {
}
