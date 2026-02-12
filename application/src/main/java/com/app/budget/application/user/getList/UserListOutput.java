package com.app.budget.application.user.getList;

import com.app.budget.domain.entities.User.User;
import com.app.budget.domain.entities.User.UserID;
import com.app.budget.domain.entities.User.enums.UserRoleType;

public record UserListOutput(
        UserID id,
        String name,
        String email,
        String password,
        UserRoleType role
) {

    public static UserListOutput from(User user) {
        return new UserListOutput(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );

    }
}
