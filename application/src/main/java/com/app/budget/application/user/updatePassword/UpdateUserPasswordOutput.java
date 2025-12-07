package com.app.budget.application.user.updatePassword;

import com.app.budget.domain.entities.User.UserID;

public record UpdateUserPasswordOutput(UserID id) {
    public static UpdateUserPasswordOutput from(UserID id) {
        return new UpdateUserPasswordOutput(id);
    }
}
