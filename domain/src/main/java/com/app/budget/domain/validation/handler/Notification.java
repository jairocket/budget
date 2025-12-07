package com.app.budget.domain.validation.handler;

import com.app.budget.domain.exceptions.DomainException;
import com.app.budget.domain.validation.Error;
import com.app.budget.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {
    private final List<Error> errors;

    private Notification(final List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Throwable throwable) {
        return new Notification(new ArrayList<>()).append(new Error(throwable.getMessage()));
    }

    public static Notification create(final Error error) {
        return new Notification(new ArrayList<>()).append(error);
    }

    @Override
    public Notification append(final Error error) {
        this.errors.add(error);
        return this;
    }

    @Override
    public Notification append(final ValidationHandler handler) {
        this.errors.addAll(handler.getErrors());
        return this;
    }

    @Override
    public Notification validate(final Validation validation) {
        try {
            validation.validate();
        } catch (final DomainException exception) {
            this.errors.addAll(exception.getErrors());
        } catch (final Throwable throwable) {
            this.errors.add(new Error(throwable.getMessage()));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}
