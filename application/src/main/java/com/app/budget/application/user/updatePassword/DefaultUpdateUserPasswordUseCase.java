package com.app.budget.application.user.updatePassword;

import com.app.budget.domain.entities.User.User;
import com.app.budget.domain.entities.User.UserID;
import com.app.budget.domain.entities.User.gateway.UserGateway;
import com.app.budget.domain.validation.Error;
import com.app.budget.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

public class DefaultUpdateUserPasswordUseCase extends UpdateUserPasswordUseCase {
    private final UserGateway userGateway;

    public DefaultUpdateUserPasswordUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public Either<Notification, UpdateUserPasswordOutput> execute(UpdateUserPasswordCommand updateUserPasswordCommand) {
        final var user = userGateway.getById(updateUserPasswordCommand.id());

        return user.isPresent() ? updatePassword(updateUserPasswordCommand, user.get()) : API.Left(Notification.create(new Error("User not found")));

    }

    private Either<Notification, UpdateUserPasswordOutput> updatePassword(UpdateUserPasswordCommand updateUserPasswordCommand, User user) {
        final var notification = Notification.create();

        final var updatedUser = user.setPassword(updateUserPasswordCommand.password());
        updatedUser.validate(notification);

        return notification.hasError() ? API.Left(notification) : to(updatedUser.getId(), updateUserPasswordCommand.password());
    }

    private Either<Notification, UpdateUserPasswordOutput> to(final UserID id, String password) {
        return API.Try(() -> this.userGateway.updatePassword(id, password))
                .toEither()
                .bimap(Notification::create, UpdateUserPasswordOutput::from);
    }

}

