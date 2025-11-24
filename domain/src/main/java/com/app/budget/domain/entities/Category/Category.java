package com.app.budget.domain.entities.Category;

import com.app.budget.domain.AggregateRoot;
import com.app.budget.domain.validation.ValidationHandler;

public class Category extends AggregateRoot<CategoryID> {
    private final String name;

    private Category(CategoryID categoryID, String name) {
        super(categoryID);
        this.name = name;
    }

    public static Category newCategory(String name) {
        final var categoryId = CategoryID.unique();
        return new Category(categoryId, name);
    }

    public String getName() {
        return name;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new CategoryValidator(handler, this).validate();
    }
}
