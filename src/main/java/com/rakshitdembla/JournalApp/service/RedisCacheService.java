package com.rakshitdembla.JournalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakshitdembla.JournalApp.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheService {

    @Autowired
    private RedisTemplate redisTemplate;

    // Add data in redis cache
    public <T> void set(String key,T value){
        try {
            redisTemplate.opsForValue().set(key,value);
        }
        catch(Exception e) {
            throw new AppException(500,e.getMessage());
        }
    }

    // Get data from redis cache
    public <T> T get(String key, Class<T> classType) {
        try {
            Object res = redisTemplate.opsForValue().get(key);

            if (res == null) {
                throw new AppException(404,"Key doesn't exists");
            }

            ObjectMapper mapper = new ObjectMapper();
            T value = mapper.convertValue(res,classType);

            return value;
        }
        catch (AppException e) {
            throw e;
        }
        catch (Exception e) {
            throw new AppException(500,e.getMessage());
        }
    }
}
