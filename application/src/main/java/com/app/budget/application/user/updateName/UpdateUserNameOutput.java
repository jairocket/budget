package com.app.budget.application.user.updateName;

import com.app.budget.domain.entities.User.UserID;

public record UpdateUserNameOutput(UserID id) {
    public static UpdateUserNameOutput from(UserID id) {
        return new UpdateUserNameOutput(id);
    }
}
