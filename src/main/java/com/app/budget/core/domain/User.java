package com.app.budget.core.domain;

import com.app.budget.core.enums.UserRole;
import com.app.budget.core.exceptions.UserException;

import java.util.Optional;
import java.util.regex.Pattern;

public class User {
    private final String email;
    private Long id;
    private String name;
    private String password;
    private UserRole role;

    public User(String name, String email, String password, UserRole role) {
        validateUserConstructor(name, email, password, role);

        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(Long id, String name, String email, String password, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (!passwordIsValid(password)) {
            throw new UserException("Inform a valid password");
        }
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        validateRole(role);
        this.role = role;
    }

    private boolean emailIsValid(String email) {
        email = Optional.ofNullable(email).orElseThrow(() -> new UserException("User email cannot be null"));

        return Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
                .matcher(email)
                .matches();
    }

    private void validateName(String name) {
        if (name == null) {
            throw new UserException("User name cannot be null");
        }

        if (name.length() < 3) {
            throw new UserException("User name should have at least three characters");
        }

        if (name.length() > 60) {
            throw new UserException("User name should have less than sixty characters");
        }
    }

    private boolean passwordIsValid(String password) {
        password = Optional.ofNullable(password).orElseThrow(() -> new UserException("User password cannot be null"));

        return Pattern.compile(
                        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$",
                        Pattern.CASE_INSENSITIVE
                )
                .matcher(password)
                .matches();
    }

    private void validateUserConstructor(String name, String email, String password, UserRole role) {
        validateName(name);
        validateRole(role);

        if (!emailIsValid(email)) {
            throw new UserException("Inform a valid email");
        }

        if (!passwordIsValid(password)) {
            throw new UserException("Inform a valid password");
        }
    }

    private void validateRole(UserRole role) {
        if (role == null) {
            throw new UserException("Role should be informed.");
        }
    }

}
