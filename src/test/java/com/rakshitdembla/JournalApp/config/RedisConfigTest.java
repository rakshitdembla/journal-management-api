package com.rakshitdembla.JournalApp.config;
import com.rakshitdembla.JournalApp.entity.UserEntry;
import com.rakshitdembla.JournalApp.service.RedisCacheService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
class RedisConfigTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisCacheService redisCacheService;

    @ParameterizedTest
    @CsvSource({
            "user1,password1",
            "user2,password2"
    })
     void testRedisConnection(String username,String password) {
        UserEntry user = new UserEntry();

        user.setUsername(username);
        user.setPassword(password);

        redisCacheService.set(username,user);
        UserEntry cacheUser = redisCacheService.get(username,UserEntry.class);

        System.out.println(cacheUser.getUsername());
    }
}
// INTEGRATE WHERE EVERY REQUIRED SPECIALLY SENTIMENT I GUESS!