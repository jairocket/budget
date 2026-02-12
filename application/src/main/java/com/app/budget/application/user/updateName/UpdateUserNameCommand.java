package com.app.budget.application.user.updateName;

import com.app.budget.domain.entities.User.UserID;

public record UpdateUserNameCommand(
        UserID id,
        String name
) {
    public static UpdateUserNameCommand with(final UserID id, final String name) {
        return new UpdateUserNameCommand(id, name);
    }
}
