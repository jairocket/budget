package com.app.budget.domain.entities.User;

import com.app.budget.domain.AggregateRoot;
import com.app.budget.domain.entities.User.enums.UserRoleType;
import com.app.budget.domain.validation.ValidationHandler;

import java.util.Objects;

public class User extends AggregateRoot<UserID> {
    private final String email;
    private String name;
    private String password;
    private String role;

    private User(
            final UserID id,
            final String name,
            final String email,
            final String password,
            final String role
    ) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static User newUser(String name, String email, String password, UserRoleType role) {
        final var id = UserID.unique();

        if (role == null) {
            role = UserRoleType.USER;
        }

        final String actualRole = role.toString();
        return new User(id, name, email, password, actualRole);
    }

    public UserID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRoleType getRole() {
        return UserRoleType.valueOf(role);
    }

    public User setRole(UserRoleType role) {
        Objects.requireNonNull(role, "Role cannot be null");
        this.role = role.toString();
        return this;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new UserValidator(handler, this).validate();
    }
}

