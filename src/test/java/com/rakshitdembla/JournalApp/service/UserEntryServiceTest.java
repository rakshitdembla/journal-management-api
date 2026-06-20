package com.rakshitdembla.JournalApp.service;
import com.rakshitdembla.JournalApp.entity.UserEntry;
import com.rakshitdembla.JournalApp.repository.UserEntryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserEntryServiceTest {

    @InjectMocks
    private UserEntryService userEntryService;

    @Mock
    private UserEntryRepository userEntryRepository;

    @Test
    void loadUserByUsername() {
        UserEntry user = new UserEntry();
        user.setUsername("Rakshit");
        user.setPassword("Hey1234");

        when(userEntryRepository.findByUsername("Rakshit"))
                .thenReturn(Optional.of(user));

        Assertions.assertNotNull(userEntryService.findByUsername("Rakshit"));
    }
}
