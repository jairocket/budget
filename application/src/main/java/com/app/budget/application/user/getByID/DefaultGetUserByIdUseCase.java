package com.app.budget.application.user.getByID;

import com.app.budget.domain.entities.User.UserID;
import com.app.budget.domain.entities.User.gateway.UserGateway;
import com.app.budget.domain.exceptions.DomainException;
import com.app.budget.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetUserByIdUseCase extends GetUserByIdUseCase {

    private final UserGateway gateway;

    public DefaultGetUserByIdUseCase(UserGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);

    }

    @Override
    public UserOutput execute(final UserID id) {
        return this.gateway.getById(id)
                .map(UserOutput::from)
                .orElseThrow(notFound(id));
    }

    private Supplier<DomainException> notFound(final UserID id) {
        return () -> DomainException.with(new Error("User with id %s not found".formatted(id.getValue())));
    }
}
