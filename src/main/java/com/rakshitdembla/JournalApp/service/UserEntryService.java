package com.rakshitdembla.JournalApp.service;

import com.rakshitdembla.JournalApp.entity.UserEntry;
import com.rakshitdembla.JournalApp.exception.AppException;
import com.rakshitdembla.JournalApp.repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserEntryService {

    @Autowired
    private UserEntryRepository userEntryRepository;

    // Find User By Username
    public UserEntry findByUsername(String username) {
        try {
            Optional<UserEntry> user = userEntryRepository.findByUsername(username);

            if (user.isPresent()) return user.get();
            throw new AppException(400, "User not found");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    // Save User
    public UserEntry saveUser(UserEntry user) {
        try {
            Optional<UserEntry> userInDb = userEntryRepository.findByUsername(user.getUsername());

            if (userInDb.isPresent()) throw new AppException(409, "Username already exists");

            return userEntryRepository.save(user);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    // Update username
    public UserEntry updateUsername(String username, String newUsername) {
        try {
            UserEntry user = findByUsername(username);
            user.setUsername(newUsername);

            return userEntryRepository.save(user);

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
}
