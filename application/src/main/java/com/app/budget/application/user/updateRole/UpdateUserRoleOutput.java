package com.app.budget.application.user.updateRole;

import com.app.budget.domain.entities.User.UserID;

public record UpdateUserRoleOutput(UserID id) {
    public static UpdateUserRoleOutput from(UserID id) {
        return new UpdateUserRoleOutput(id);
    }
}
