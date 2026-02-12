package com.app.budget.application.user.updateRole;

import com.app.budget.domain.entities.User.UserID;
import com.app.budget.domain.entities.User.enums.UserRoleType;

public record UpdateUserRoleCommand(UserID id, UserRoleType role) {
    public static UpdateUserRoleCommand with(final UserID id, final UserRoleType role) {
        return new UpdateUserRoleCommand(id, role);
    }
}
