package com.app.budget.domain;

import com.app.budget.exceptions.UserCreationException;

public class User {
    private final String email;
    private Long id;
    private String name;
    private String password;

    public User(String name, String email, String password) {
        validateIfUserConstructor(name, email, password);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(Long id, String name, String email, String password) {
        validateIfUserConstructor(name, email, password);
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
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    private void validateIfUserConstructor(String name, String email, String password) {
        if (name == null) {
            throw new UserCreationException("User name cannot be null");
        }
        if (email == null) {
            throw new UserCreationException("User email cannot be null");
        }
        if (password == null) {
            throw new UserCreationException("User password cannot be null");
        }

//        if(name.length() < 5) {
//            throw new UserCreationException("User name cannot be null");
//        }
    }


}
