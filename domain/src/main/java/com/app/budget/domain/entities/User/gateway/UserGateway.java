package com.app.budget.domain.entities.User.gateway;

import com.app.budget.domain.entities.User.User;
import com.app.budget.domain.entities.User.UserID;
import com.app.budget.domain.entities.User.UserSearchQuery;
import com.app.budget.domain.entities.User.enums.UserRoleType;
import com.app.budget.domain.pagination.Pagination;

import java.util.Optional;

public interface UserGateway {
    User save(User user);

    void deleteById(UserID id);

    Optional<User> getById(UserID id);

    void updatePassword(UserID id, String password);

    void updateName(UserID id, String name);

    void updateRole(UserID id, UserRoleType role);

    Pagination<User> findAll(UserSearchQuery query);
}
