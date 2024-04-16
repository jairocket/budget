package com.app.budget.core.domain;

import com.app.budget.core.exceptions.CategoryException;

import java.util.Optional;

public class Category {
    private Long id;
    private String name;

    public Category(Long id, String name) {
        validateName(name);

        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        validateName(name);
        this.name = name;
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

    private void validateName(String name) {
        name = Optional.ofNullable(name).orElseThrow(() -> new CategoryException("Category name cannot be null"));

        if (name.length() < 3) {
            throw new CategoryException("User category should have at least three characters");
        }

        if (name.length() > 45) {
            throw new CategoryException("User category should have less than forty-five characters");
        }
    }
}
