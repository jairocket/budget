package com.app.budget.domain.validation.handler;

import com.app.budget.domain.exceptions.DomainException;
import com.app.budget.domain.validation.Error;
import com.app.budget.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(final Error error) {
        throw DomainException.with(List.of(error));
    }

    @Override
    public ValidationHandler append(final ValidationHandler handler) {
        throw DomainException.with(handler.getErrors());
    }

    @Override
    public ValidationHandler validate(final Validation validation) {
        try {
            validation.validate();
        } catch (Exception exception) {
            throw DomainException.with(List.of(new Error(exception.getMessage())));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}
