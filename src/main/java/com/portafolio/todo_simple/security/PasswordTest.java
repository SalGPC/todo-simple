package com.portafolio.todo_simple.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordTest {

    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
    public void testPassword() {
        // Ahora sin el prefijo {bcrypt}
        String hash = "{bcrypt}$2a$12$2FCcLtM8TOclD1XMxaHINO9PiY5XJrEwO8uI8u5O5xJy1iEu/dCaS";
        System.out.println("00 Matches test123? " + encoder.matches("test123", hash));
    }
}