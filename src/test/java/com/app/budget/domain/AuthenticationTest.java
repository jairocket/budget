package com.app.budget.domain;

import org.junit.Test;

import static com.app.budget.domain.Authentication.hash;
import static com.app.budget.domain.Authentication.passwordMatches;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthenticationTest {
    @Test
    public void shouldHashPassword() {
        var rawPassword = "P@ink1ller";
        var hashedPassword = hash(rawPassword);

        assertFalse(rawPassword.equals(hashedPassword));
        assertTrue(passwordMatches(rawPassword, hashedPassword));
    }

}
