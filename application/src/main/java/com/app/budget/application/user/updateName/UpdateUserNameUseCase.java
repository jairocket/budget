package com.app.budget.application.user.updateName;

import com.app.budget.application.UseCase;
import com.app.budget.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateUserNameUseCase extends UseCase<UpdateUserNameCommand, Either<Notification, UpdateUserNameOutput>> {
}
