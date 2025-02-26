package com.app.budget.infrastructure.persistence.entities;

import org.springframework.stereotype.Component;

@Component
public class CategoryEntity {
    private Long id;
    private String name;

    public CategoryEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryEntity(String name) {
        this.name = name;
    }

    public CategoryEntity() {
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
}
