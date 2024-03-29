package com.app.budget.domain;

import com.app.budget.exceptions.UserCreationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void shouldBeAbleToCreateUser() {
        Long id = 1L;
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "ThePassWord";

        User user = new User(id, name, email, password);

        assertEquals("New User", user.getName());
        assertEquals("new.user@budget.com", user.getEmail());
        assertTrue(1 == user.getId());
    }

    @Test
    public void shouldThrowErrorIfNameIsNull() {
        String email = "new.user@budget.com";
        String password = "ThePassWord";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(null, email, password));
        assertEquals("User name cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowErrorIfEmailIsNull() {
        String name = "New User";
        String password = "ThePassWord";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(name, null, password));
        assertEquals("User email cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowErrorIfPassWordIsNull() {
        String name = "New User";
        String email = "new.user@budget.com";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(name, email, null));
        assertEquals("User password cannot be null", exception.getMessage());
    }
}
