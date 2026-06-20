package com.rakshitdembla.JournalApp.repository;

import com.rakshitdembla.JournalApp.entity.UserEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserEntryImplRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    // Get list of sentimential analysis users
    public List<UserEntry> getSAusers() {
        Query query = new Query();

        query.addCriteria(Criteria.where("sentimentalAnalysis").exists(true).is(true));
        return mongoTemplate.find(query,UserEntry.class);
    }
}
