package com.app.budget.domain;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {
    @Test
    public void shouldBeAbleToCreateUser(){
        Long id = 1l;
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "ThePassWord";

        User user = new User(id, name, email, password);

        assertEquals("New User", user.getName());
        assertEquals("new.user@budget.com", user.getEmail());
        assertTrue(user.getId() == 1);
    }
}
