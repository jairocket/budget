package com.app.budget.application.user.getList;

import com.app.budget.domain.entities.User.User;
import com.app.budget.domain.entities.User.UserSearchQuery;
import com.app.budget.domain.entities.User.enums.UserRoleType;
import com.app.budget.domain.entities.User.gateway.UserGateway;
import com.app.budget.domain.pagination.Pagination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GetUserListUseCaseTest {

    @InjectMocks
    private DefaultGetUserListUserCase useCase;

    @Mock
    private UserGateway userGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(userGateway);
    }

    @Test
    public void given_valid_query_when_call_get_user_list_should_return_paginated_user_list() {
        final var page = 0;
        final var perPage = 10;
        final var terms = "";
        final var sort = "name";
        final var direction = "asc";
        final var query = new UserSearchQuery(page, perPage, terms, sort, direction);

        final var users = List.of(
                User.newUser("user1", "user1@email.com", "P!p0ca85", UserRoleType.USER),
                User.newUser("user2", "user2@email.com", "P!p0ca85", UserRoleType.USER)
        );

        final var expectedPagination = new Pagination<>(page, perPage, users.size(), users);

        final var expectedItems = 2;
        final var expectedResult = expectedPagination.map(UserListOutput::from);

        Mockito.when(userGateway.findAll(Mockito.eq(query))).thenReturn(expectedPagination);

        final var actualResult = useCase.execute(query);

        Assertions.assertEquals(expectedItems, actualResult.items().size());
        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(page, actualResult.currentPage());
        Assertions.assertEquals(perPage, actualResult.perPage());
        Assertions.assertEquals(expectedItems, actualResult.total());
    }

    @Test
    public void given_valid_query_when_no_users_are_found_return_empty_list() {
        final var page = 0;
        final var perPage = 10;
        final var terms = "";
        final var sort = "name";
        final var direction = "asc";
        final var query = new UserSearchQuery(page, perPage, terms, sort, direction);

        final var users = List.<User>of();

        final var expectedPagination = new Pagination<>(page, perPage, users.size(), users);

        final var expectedItems = 0;
        final var expectedResult = expectedPagination.map(UserListOutput::from);

        Mockito.when(userGateway.findAll(Mockito.eq(query))).thenReturn(expectedPagination);

        final var actualResult = useCase.execute(query);

        Assertions.assertEquals(expectedItems, actualResult.items().size());
        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(page, actualResult.currentPage());
        Assertions.assertEquals(perPage, actualResult.perPage());
        Assertions.assertEquals(expectedItems, actualResult.total());
    }

    @Test
    public void given_valid_query_when_gateway_throws_exception_should_return_exception() {
        final var page = 0;
        final var perPage = 10;
        final var terms = "";
        final var sort = "name";
        final var direction = "asc";
        final var query = new UserSearchQuery(page, perPage, terms, sort, direction);
        final var expectedErrorMessage = "Gateway Error";

        Mockito.when(userGateway.findAll(Mockito.eq(query))).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(query));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

    }
}
