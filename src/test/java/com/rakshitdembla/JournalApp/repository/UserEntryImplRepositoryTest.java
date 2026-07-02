package com.rakshitdembla.JournalApp.repository;

import com.rakshitdembla.JournalApp.entity.UserEntry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

@SpringBootTest
@Slf4j
public class UserEntryImplRepositoryTest {

    @Autowired
    private UserEntryImplRepository userEntryImplRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    public void testSAusers() {

        List<UserEntry> users= userEntryImplRepository.getSAusers();
        log.info(users.toString());

        Assertions.assertNotNull(users);
    }
}
