package com.app.budget.application.user.getList;

import com.app.budget.application.UseCase;
import com.app.budget.domain.entities.User.UserSearchQuery;
import com.app.budget.domain.pagination.Pagination;

public abstract class GetUserListUseCase extends UseCase<UserSearchQuery, Pagination<UserListOutput>> {
}
