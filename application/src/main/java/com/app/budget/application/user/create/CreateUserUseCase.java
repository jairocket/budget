package com.app.budget.application.user.create;


import com.app.budget.application.UseCase;
import com.app.budget.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateUserUseCase extends UseCase<CreateUserCommand, Either<Notification, CreateUserOutput>> {

}
