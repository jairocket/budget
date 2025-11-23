package com.app.budget.domain.validation;

import java.util.List;

public interface ValidationHandler {
    ValidationHandler append(Error error);

    ValidationHandler append(ValidationHandler handler);

    ValidationHandler validate(Validation validation);

    List<Error> getErrors();

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    public interface Validation {
        void validate();
    }
}
