package com.rakshitdembla.JournalApp.service;

import com.rakshitdembla.JournalApp.entity.UserEntry;
import com.rakshitdembla.JournalApp.exception.AppException;
import com.rakshitdembla.JournalApp.repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserEntryService {

    @Autowired
    private UserEntryRepository userEntryRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Find User By Username
    public UserEntry findByUsername(String username) {
        try {
            Optional<UserEntry> user = userEntryRepository.findByUsername(username);

            if (user.isPresent()) return user.get();
            throw new AppException(404, "User not found");
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

            // Encode password before save
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            // Add role as "USER"
            user.setRoles(new ArrayList<String>(List.of("USER")));

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

            Optional<UserEntry> existingUser =
                    userEntryRepository.findByUsername(newUsername);

            if (existingUser.isPresent()) {
                throw new AppException(409, "Username already exists");
            }

            user.setUsername(newUsername);

            return userEntryRepository.save(user);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    // Delete User
    public boolean deleteUserByUsername(String username) {
        try {
            userEntryRepository.deleteByUsername(username);
            return true;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    // Get all users - #ADMIN ROLE REQUIRED
    public List<UserEntry> getAllUsers() {
        try {
            return userEntryRepository.findAll();
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    // Make user admin
    public boolean promoteToAdmin(String username) {
        UserEntry user = findByUsername(username);

        try {
            if (user.getRoles().contains("ADMIN")) {
                throw new AppException(409, "User is already an admin!");
            }

            user.getRoles().add("ADMIN");
            userEntryRepository.save(user);

            return true;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
}
