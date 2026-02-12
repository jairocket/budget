package com.app.budget.domain.entities.User;

public record UserSearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
