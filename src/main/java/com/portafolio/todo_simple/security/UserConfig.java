package com.portafolio.todo_simple.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Option 1: delegating password encoder
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // Option 2: Use BCrypt directly
        // return new BCryptPasswordEncoder();
    }
}
