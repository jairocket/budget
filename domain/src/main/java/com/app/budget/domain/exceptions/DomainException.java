package com.app.budget.domain.exceptions;

import com.app.budget.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException {
    private final List<Error> errors;

    private DomainException(final List<Error> errors) {
        super("");
        this.errors = errors;
    }

    public static DomainException with(final List<Error> errors) {
        return new DomainException(errors);
    }

    public static DomainException with(final Error error) {
        return new DomainException(List.of(error));
    }

    public List<Error> getErrors() {
        return errors;
    }
}
