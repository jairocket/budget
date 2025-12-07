package com.app.budget.domain.entities.User;

import com.app.budget.domain.AggregateRoot;
import com.app.budget.domain.entities.User.enums.UserRoleType;
import com.app.budget.domain.validation.ValidationHandler;

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

    public void setName(String name) {
        this.name = name;
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

    public void setRole(UserRoleType role) {
        this.role = role.toString();
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new UserValidator(handler, this).validate();
    }
}

