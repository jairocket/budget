package com.app.budget.application.user.updatePassword;

import com.app.budget.application.UseCase;
import com.app.budget.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateUserPasswordUseCase extends UseCase<UpdateUserPasswordCommand, Either<Notification, UpdateUserPasswordOutput>> {
}
