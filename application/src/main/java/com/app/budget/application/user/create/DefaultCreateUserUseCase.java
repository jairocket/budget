package com.app.budget.application.user.create;

import com.app.budget.domain.entities.User.User;
import com.app.budget.domain.entities.User.gateway.UserGateway;
import com.app.budget.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultCreateUserUseCase extends CreateUserUseCase {
    private final UserGateway userGateway;

    public DefaultCreateUserUseCase(UserGateway userGateway) {
        this.userGateway = Objects.requireNonNull(userGateway);
    }

    @Override
    public Either<Notification, CreateUserOutput> execute(final CreateUserCommand createUserCommand) {
        final var user = User.newUser(
                createUserCommand.name(),
                createUserCommand.email(),
                createUserCommand.password(),
                createUserCommand.role()
        );
        final var notification = Notification.create();
        user.validate(notification);

        return notification.hasError() ? API.Left(notification) : create(user);
    }

    private Either<Notification, CreateUserOutput> create(final User user) {
        return API.Try(() -> this.userGateway.create(user))
                .toEither()
                .bimap(Notification::create, CreateUserOutput::from);

    }
}
