package com.rakshitdembla.JournalApp.cache;

import com.rakshitdembla.JournalApp.entity.ConfigEntity;
import com.rakshitdembla.JournalApp.repository.AppConfigRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppConfigCache {

    public enum keys {
        API_PATH,
    }

    @Autowired
    private AppConfigRepository appConfigRepository;

    public Map<String,String> cacheMap;

    @PostConstruct
    public void init() {
        cacheMap = new HashMap<>();

        List<ConfigEntity> configs = appConfigRepository.findAll();

        for (ConfigEntity config : configs) {
            cacheMap.put(config.getKey(),config.getValue());
        }
    }
}
