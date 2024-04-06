package com.app.budget.domain;

import com.app.budget.enums.UserRole;
import com.app.budget.exceptions.UserCreationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void shouldBeAbleToCreateUser() {
        Long id = 1L;
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "P@inK1ller";
        String role = "USER";

        User user = new User(id, name, email, password, UserRole.valueOf(role));

        assertEquals("New User", user.getName());
        assertEquals("new.user@budget.com", user.getEmail());
        assertTrue(1 == user.getId());
        assertNotEquals(password, user.getPassword());
        assertEquals(user.getRole(), UserRole.USER);
    }

    @Test
    public void shouldThrowExceptionIfPassWordIsInvalid() {
        Long id = 1L;
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "ThePassWord";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(id, name, email, password, UserRole.USER));
        assertEquals("Inform a valid password", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfNameIsNull() {
        String email = "new.user@budget.com";
        String password = "ThePassWord";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(null, email, password, UserRole.ADMIN));
        assertEquals("User name cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfRoleIsNull() {
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "P@inK1ller";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(name, email, password, null));
        assertEquals("Role should be informed.", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfEmailIsNull() {
        String name = "New User";
        String password = "ThePassWord";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(name, null, password, UserRole.USER));
        assertEquals("User email cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfEmailIsInvalid() {
        String name = "New User";
        String email = "new.user#budget.com";
        String password = "ThePassWord";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(name, email, password, UserRole.USER));
        assertEquals("Inform a valid email", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfPassWordIsNull() {
        String name = "New User";
        String email = "new.user@budget.com";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(name, email, null, UserRole.ADMIN));
        assertEquals("User password cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfNameHasLessThanThreeCharacters() {
        String name = "Ne";
        String email = "new.user@budget.com";
        String password = "ThePassWord";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(name, email, password, UserRole.USER));
        assertEquals("User name should have at least three characters", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfNameHasMoreThanSixtyCharacters() {
        String longName = "qwertyuiopasdfghjklzxcvbnmmnbvcxzlkjhgfdsapoiuytrewqqwertyuiop";
        String email = "new.user@budget.com";
        String password = "ThePassWord";

        UserCreationException exception = assertThrows(UserCreationException.class, () -> new User(longName, email, password, UserRole.USER));
        assertEquals("User name should have less than sixty characters", exception.getMessage());
    }

    @Test
    public void shouldBeAbleToUpdateUserName() {
        Long id = 1L;
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "P@inK1ller";
        User user = new User(id, name, email, password, UserRole.ADMIN);

        user.setName("Updated User");

        assertEquals("Updated User", user.getName());
    }

    @Test
    public void shouldValidateNameBeforeUpdateIt() {
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "P@inK1ller";
        User user = new User(name, email, password, UserRole.USER);

        UserCreationException exception = assertThrows(UserCreationException.class, () -> user.setName("Me"));
        assertEquals("User name should have at least three characters", exception.getMessage());
    }

    @Test
    public void shouldBeAbleToUpdateUserPassword() {
        Long id = 1L;
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "P@inK1ller";
        User user = new User(id, name, email, password, UserRole.USER);

        user.setPassword("Upd@t&d1");

        assertEquals("Upd@t&d1", user.getPassword());
    }

    @Test
    public void shouldValidatePasswordBeforeUpdateIt() {
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "P@inK1ller";
        User user = new User(name, email, password, UserRole.USER);

        UserCreationException exception = assertThrows(UserCreationException.class, () -> user.setPassword("ThePassword"));
        assertEquals("Inform a valid password", exception.getMessage());
    }
}
