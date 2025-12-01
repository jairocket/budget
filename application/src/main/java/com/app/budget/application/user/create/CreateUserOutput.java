package com.app.budget.application.user.create;

import com.app.budget.domain.entities.User.User;
import com.app.budget.domain.entities.User.UserID;

public record CreateUserOutput(UserID id) {
    public static CreateUserOutput from(final User user) {
        return new CreateUserOutput(user.getId());
    }
}
