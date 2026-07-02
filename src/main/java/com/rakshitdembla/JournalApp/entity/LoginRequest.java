package com.rakshitdembla.JournalApp.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class LoginRequest {

    private String username;

    private String password;

}
