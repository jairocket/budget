package com.app.budget.application.user.updateRole;

import com.app.budget.domain.entities.User.User;
import com.app.budget.domain.entities.User.UserID;
import com.app.budget.domain.entities.User.enums.UserRoleType;
import com.app.budget.domain.entities.User.gateway.UserGateway;
import com.app.budget.domain.validation.Error;
import com.app.budget.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

public class DefaultUpdateRoleUseCase extends UpdateUserRoleUseCase {
    private final UserGateway userGateway;

    public DefaultUpdateRoleUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public Either<Notification, UpdateUserRoleOutput> execute(UpdateUserRoleCommand updateUserRoleCommand) {
        final var user = userGateway.getById(updateUserRoleCommand.id());

        return user.isPresent() ? updateRole(updateUserRoleCommand, user.get()) : API.Left(Notification.create(new Error("User not found")));

    }

    private Either<Notification, UpdateUserRoleOutput> updateRole(UpdateUserRoleCommand updateUserRoleCommand, User user) {
        final var notification = Notification.create();

        final var updatedUser = user.setRole(updateUserRoleCommand.role());
        updatedUser.validate(notification);

        return notification.hasError() ? API.Left(notification) : to(updatedUser.getId(), updateUserRoleCommand.role());
    }

    private Either<Notification, UpdateUserRoleOutput> to(final UserID id, UserRoleType role) {
        return API.Try(() -> this.userGateway.updateRole(id, role))
                .toEither()
                .bimap(Notification::create, UpdateUserRoleOutput::from);
    }
}
