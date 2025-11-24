package com.app.budget.domain.User;

import com.app.budget.domain.User.enums.UserRoleType;
import com.app.budget.domain.exceptions.DomainException;
import com.app.budget.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void given_valid_params_then_should_be_able_to_create_user() {
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "P@inK1ller";
        UserRoleType role = UserRoleType.USER;

        User user = User.newUser(name, email, password, role);
        assertEquals("new.user@budget.com", user.getEmail());
        assertEquals("New User", user.getName());
        assertEquals(UserRoleType.USER, user.getRole());
        assertNotNull(user.getId());
        assertDoesNotThrow(() -> user.validate(new ThrowsValidationHandler()));
    }

    @Test
    public void given_invalid_password_when_user_is_validated_should_throw_error() {
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "ThePassWord";

        var user = User.newUser(name, email, password, UserRoleType.USER);

        var exception = assertThrows(DomainException.class, () -> user.validate(new ThrowsValidationHandler()));
        assertEquals("Inform a valid password", exception.getErrors().getFirst().message());
        assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void given_null_name_when_user_is_validate_user_should_throw_error() {
        String email = "new.user@budget.com";
        String password = "P@inK1ller";

        var user = User.newUser(null, email, password, UserRoleType.USER);

        var exception = assertThrows(DomainException.class, () -> user.validate(new ThrowsValidationHandler()));
        assertEquals("User name cannot be null", exception.getErrors().getFirst().message());
        assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void given_null_role_should_create_user_with_default_role_type() {
        String name = "New User";
        String email = "new.user@budget.com";
        String password = "P@inK1ller";

        var user = User.newUser(name, email, password, null);

        assertEquals("new.user@budget.com", user.getEmail());
        assertEquals("New User", user.getName());
        assertEquals(UserRoleType.USER, user.getRole());
        assertNotNull(user.getId());
        assertDoesNotThrow(() -> user.validate(new ThrowsValidationHandler()));
    }

    @Test
    public void shouldThrowExceptionIfEmailIsNull() {
        String name = "New User";
        String password = "P@inK1ller";

        var user = User.newUser(name, null, password, UserRoleType.USER);

        var exception = assertThrows(DomainException.class, () -> user.validate(new ThrowsValidationHandler()));
        assertEquals("User email cannot be null", exception.getErrors().getFirst().message());
        assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void shouldThrowExceptionIfEmailIsInvalid() {
        String name = "New User";
        String email = "new.user#budget.com";
        String password = "P@inK1ller";

        var user = User.newUser(name, email, password, UserRoleType.USER);

        var exception = assertThrows(DomainException.class, () -> user.validate(new ThrowsValidationHandler()));
        assertEquals("User email is invalid", exception.getErrors().getFirst().message());
        assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void given_null_password_when_validate_should_throw_exception() {
        String name = "New User";
        String email = "new.user@budget.com";

        var user = User.newUser(name, email, null, UserRoleType.USER);
        var exception = assertThrows(DomainException.class, () -> user.validate(new ThrowsValidationHandler()));

        assertEquals("User password cannot be null", exception.getErrors().getFirst().message());
        assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void given_username_with_less_than_three_characters_when_validate_should_throw_exception() {
        String name = "Ne";
        String email = "new.user@budget.com";
        String password = "P@inK1ller";

        var user = User.newUser(name, email, password, UserRoleType.USER);
        var exception = assertThrows(DomainException.class, () -> user.validate(new ThrowsValidationHandler()));

        assertEquals("User name should have at least three characters", exception.getErrors().getFirst().message());
        assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void given_username_with_more_than_sixty_characters_when_validate_should_throw_exception() {
        String longName = "qwertyuiopasdfghjklzxcvbnmmnbvcxzlkjhgfdsapoiuytrewqqwertyuiop";
        String email = "new.user@budget.com";
        String password = "P@inK1ller";

        var user = User.newUser(longName, email, password, UserRoleType.USER);
        var exception = assertThrows(DomainException.class, () -> user.validate(new ThrowsValidationHandler()));

        assertEquals("User name should have less than sixty characters", exception.getErrors().getFirst().message());
        assertEquals(1, exception.getErrors().size());

    }
}
