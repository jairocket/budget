package com.app.budget.domain.Category;

import com.app.budget.domain.validation.Error;
import com.app.budget.domain.validation.ValidationHandler;
import com.app.budget.domain.validation.Validator;

public class CategoryValidator extends Validator {
    private final Category category;

    public CategoryValidator(ValidationHandler handler, Category category) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        if (this.category.getName() == null) {
            this.validationHandler().append(new Error("Category name cannot be null"));
        }

        if (this.category.getName().length() < 3) {
            this.validationHandler().append(new Error("User category should have at least three characters"));
        }

        if (this.category.getName().length() > 45) {
            this.validationHandler().append(new Error("User category should have less than forty-five characters"));
        }
    }
}
