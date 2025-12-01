package com.app.budget.application.user.create;

import com.app.budget.domain.entities.User.User;
import com.app.budget.domain.entities.User.gateway.UserGateway;
import com.app.budget.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateUserUseCase extends CreateUserUseCase {
    private final UserGateway userGateway;

    public DefaultCreateUserUseCase(UserGateway userGateway) {
        this.userGateway = Objects.requireNonNull(userGateway);
    }

    @Override
    public CreateUserOutput execute(final CreateUserCommand createUserCommand) {
        final var user = User.newUser(
                createUserCommand.name(),
                createUserCommand.email(),
                createUserCommand.password(),
                createUserCommand.role()
        );
        user.validate(new ThrowsValidationHandler());

        return CreateUserOutput.from(this.userGateway.create(user));
    }


}
