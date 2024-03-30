package com.app.budget.domain;

import com.app.budget.exceptions.UserCreationException;

import java.util.Optional;
import java.util.regex.Pattern;

public class User {
    private final String email;
    private Long id;
    private String name;
    private String password;

    public User(String name, String email, String password) {
        validateUserConstructor(name, email, password);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(Long id, String name, String email, String password) {
        validateUserConstructor(name, email, password);
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
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
            throw new UserCreationException("Inform a valid password");
        }

        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    private boolean emailIsValid(String email) {
        email = Optional.ofNullable(email).orElseThrow(() -> new UserCreationException("User email cannot be null"));

        return Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
                .matcher(email)
                .matches();
    }

    private void validateName(String name) {
        if (name == null) {
            throw new UserCreationException("User name cannot be null");
        }

        if (name.length() < 3) {
            throw new UserCreationException("User name should have at least three characters");
        }
    }

    private boolean passwordIsValid(String password) {
        password = Optional.ofNullable(password).orElseThrow(() -> new UserCreationException("User password cannot be null"));

        return Pattern.compile(
                        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$",
                        Pattern.CASE_INSENSITIVE
                )
                .matcher(password)
                .matches();
    }

    private void validateUserConstructor(String name, String email, String password) {
        validateName(name);

        if (!emailIsValid(email)) {
            throw new UserCreationException("Inform a valid email");
        }

        if (!passwordIsValid(password)) {
            throw new UserCreationException("Inform a valid password");
        }

    }

}
