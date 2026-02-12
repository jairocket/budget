package com.app.budget.application.user.create;


import com.app.budget.domain.entities.User.enums.UserRoleType;

public record CreateUserCommand(
        String name,
        String email,
        String password,
        UserRoleType role
) {
    public static CreateUserCommand with(
            final String name,
            final String email,
            final String password,
            final UserRoleType role
    ) {
        return new CreateUserCommand(name, email, password, role);
    }
}
