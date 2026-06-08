package com.rakshitdembla.JournalApp.service;

import com.rakshitdembla.JournalApp.entity.UserEntry;
import com.rakshitdembla.JournalApp.repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserEntryRepository userEntryRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntry> userInDb = userEntryRepository.findByUsername(username);

        if (userInDb.isPresent()) {
            UserEntry user = userInDb.get();
            UserDetails userDetails = User.builder().username(user.getUsername()).password(user.getPassword()).roles(user.getRoles().toArray(new String[0])).build();

            return userDetails;
        }

        throw new UsernameNotFoundException("Username not found!");
    }
}
