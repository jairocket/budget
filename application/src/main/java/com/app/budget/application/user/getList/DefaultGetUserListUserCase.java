package com.app.budget.application.user.getList;

import com.app.budget.domain.entities.User.UserSearchQuery;
import com.app.budget.domain.entities.User.gateway.UserGateway;
import com.app.budget.domain.pagination.Pagination;

public class DefaultGetUserListUserCase extends GetUserListUseCase {
    private final UserGateway userGateway;

    public DefaultGetUserListUserCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public Pagination<UserListOutput> execute(UserSearchQuery userSearchQuery) {
        return this.userGateway.findAll(userSearchQuery).map(UserListOutput::from);
    }
}
