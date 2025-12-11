package com.app.budget.application.user.updateRole;

import com.app.budget.application.UseCase;
import com.app.budget.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateUserRoleUseCase extends UseCase<UpdateUserRoleCommand, Either<Notification, UpdateUserRoleOutput>> {

}
