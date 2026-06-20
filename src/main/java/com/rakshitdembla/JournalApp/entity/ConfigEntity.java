package com.rakshitdembla.JournalApp.entity;

import com.mongodb.lang.NonNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "journal_app_config")
@Data
public class ConfigEntity {

    @Id
    private ObjectId id;

    @NonNull
    private String key;

    @NonNull
    private String value;
}
