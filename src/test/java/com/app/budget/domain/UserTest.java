package com.app.budget.domain;

import com.app.budget.exceptions.UserCreationException;
import org.junit.Test;

import static com.app.budget.domain.Authentication.passwordMatches;
import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void shouldBeAbleToCreateUser() {
        Long id = 1L;
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "P@inK1ller";

        User user = new User(id, name, email, password);

        assertEquals("New User", user.getName());
        assertEquals("new.user@budget.com", user.getEmail());
        assertTrue(1 == user.getId());
        assertNotEquals(password, user.getPassword());
        assertTrue(passwordMatches(password, user.getPassword()));
    }

    @Test
    public void shouldThrowExceptionIfPassWordIsInvalid() {
        Long id = 1L;
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "ThePassWord";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(id, name, email, password));
        assertEquals("Inform a valid password", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfNameIsNull() {
        String email = "new.user@budget.com";
        String password = "ThePassWord";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(null, email, password));
        assertEquals("User name cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfEmailIsNull() {
        String name = "New User";
        String password = "ThePassWord";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(name, null, password));
        assertEquals("User email cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfEmailIsInvalid() {
        String name = "New User";
        String email = "new.user#budget.com";
        String password = "ThePassWord";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(name, email, password));
        assertEquals("Inform a valid email", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfPassWordIsNull() {
        String name = "New User";
        String email = "new.user@budget.com";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(name, email, null));
        assertEquals("User password cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfNameHasLessThanThreeCharacters() {
        String name = "Ne";
        String email = "new.user@budget.com";
        String password = "ThePassWord";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(name, email, password));
        assertEquals("User name should have at least three characters", exception.getMessage());
    }

    @Test
    public void shouldBeAbleToUpdateUserName() {
        Long id = 1L;
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "P@inK1ller";
        User user = new User(id, name, email, password);

        user.setName("Updated User");

        assertEquals("Updated User", user.getName());
    }

    @Test
    public void shouldValidateNameBeforeUpdateIt() {
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "P@inK1ller";
        User user = new User(name, email, password);

        UserCreationException exception = assertThrows(UserCreationException.class, () -> user.setName("Me"));
        assertEquals("User name should have at least three characters", exception.getMessage());
    }

    @Test
    public void shouldBeAbleToUpdateUserPassword() {
        Long id = 1L;
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "P@inK1ller";
        User user = new User(id, name, email, password);

        user.setPassword("Upd@t&d1");

        assertEquals("Upd@t&d1", user.getPassword());
    }

    @Test
    public void shouldValidatePasswordBeforeUpdateIt() {
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "P@inK1ller";
        User user = new User(name, email, password);

        UserCreationException exception = assertThrows(UserCreationException.class, () -> user.setPassword("ThePassword"));
        assertEquals("Inform a valid password", exception.getMessage());
    }
}
