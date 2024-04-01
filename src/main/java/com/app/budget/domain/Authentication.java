package com.app.budget.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

public class Authentication {

    private static final Integer HASH_STRENGTH = 16;
    private static final SecureRandom random = new SecureRandom();

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(HASH_STRENGTH, random);


    public static String hash(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public static boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
