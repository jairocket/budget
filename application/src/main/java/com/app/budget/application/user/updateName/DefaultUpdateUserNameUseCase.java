package com.app.budget.application.user.updateName;

import com.app.budget.domain.entities.User.User;
import com.app.budget.domain.entities.User.UserID;
import com.app.budget.domain.entities.User.gateway.UserGateway;
import com.app.budget.domain.validation.Error;
import com.app.budget.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

public class DefaultUpdateUserNameUseCase extends UpdateUserNameUseCase {
    private final UserGateway userGateway;

    public DefaultUpdateUserNameUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public Either<Notification, UpdateUserNameOutput> execute(UpdateUserNameCommand updateUserNameCommand) {
        final var user = userGateway.getById(updateUserNameCommand.id());

        return user.isPresent() ? updateName(updateUserNameCommand, user.get()) : API.Left(Notification.create(new Error("User not found")));

    }

    private Either<Notification, UpdateUserNameOutput> updateName(UpdateUserNameCommand updateUserNameCommand, User user) {
        final var notification = Notification.create();

        final var updatedUser = user.setName(updateUserNameCommand.name());
        updatedUser.validate(notification);

        return notification.hasError() ? API.Left(notification) : to(updatedUser.getId(), updateUserNameCommand.name());
    }

    private Either<Notification, UpdateUserNameOutput> to(final UserID id, String name) {
        return API.Try(() -> this.userGateway.updateName(id, name))
                .toEither()
                .bimap(Notification::create, UpdateUserNameOutput::from);
    }
}