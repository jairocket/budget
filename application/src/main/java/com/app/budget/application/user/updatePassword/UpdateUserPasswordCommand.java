package com.app.budget.application.user.updatePassword;

import com.app.budget.domain.entities.User.UserID;

public record UpdateUserPasswordCommand(
        UserID id,
        String password
) {
    public static UpdateUserPasswordCommand with(final UserID id, final String password) {
        return new UpdateUserPasswordCommand(id, password);
    }
}
