package com.app.budget.application.user.getByID;

import com.app.budget.domain.entities.User.User;
import com.app.budget.domain.entities.User.UserID;
import com.app.budget.domain.entities.User.enums.UserRoleType;

public record UserOutput(
        UserID id,
        String name,
        String email,
        UserRoleType role
) {
    public static UserOutput from(final User user) {
        return new UserOutput(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
