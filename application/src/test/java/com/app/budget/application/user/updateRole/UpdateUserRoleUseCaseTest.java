package com.app.budget.application.user.updateRole;

import com.app.budget.domain.entities.User.User;
import com.app.budget.domain.entities.User.UserID;
import com.app.budget.domain.entities.User.enums.UserRoleType;
import com.app.budget.domain.entities.User.gateway.UserGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateUserRoleUseCaseTest {

    @InjectMocks
    private DefaultUpdateRoleUseCase useCase;

    @Mock
    private UserGateway userGateway;

    @Test
    public void given_a_valid_command_when_update_user_password_should_return_success() {
        final var expectedName = "User";
        final var expectedEmail = "user@email.com";
        final var oldRole = UserRoleType.ADMIN;
        final var expectedPassword = "P@ink1ller2";
        final var expectedRole = UserRoleType.USER;
        final var user = User.newUser(expectedName, expectedEmail, expectedPassword, oldRole);

        final var command = UpdateUserRoleCommand.with(user.getId(), expectedRole);

        Mockito.when(userGateway.getById(Mockito.eq(user.getId())))
                .thenReturn(Optional.of(user));

        Mockito.when(userGateway.updateRole(any(), any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(command).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(userGateway, Mockito.times(1))
                .getById(Mockito.eq(user.getId()));

        verify(userGateway, Mockito.times(1))
                .updateRole(user.getId(), expectedRole);
    }

    @Test
    public void given_null_role_when_update_role_should_return_domain_exception() {
        final var expectedName = "User";
        final var expectedEmail = "user@email.com";
        final var oldRole = UserRoleType.ADMIN;
        final var expectedPassword = "P@ink1ller2";
        final var user = User.newUser(expectedName, expectedEmail, expectedPassword, oldRole);

        final var expectedErrorMessage = "Role cannot be null";

        Mockito.when(userGateway.getById(Mockito.eq(user.getId())))
                .thenReturn(Optional.of(user));

        final var command = UpdateUserRoleCommand.with(
                user.getId(),
                null
        );

        final var exception = assertThrows(NullPointerException.class, () -> useCase.execute(command).getLeft());

        assertEquals(expectedErrorMessage, exception.getMessage());
        verify(userGateway, times(0)).updateRole(any(), any());
    }

    @Test
    public void given_user_not_found_when_create_user_should_return_domain_exception() {
        final var expectRole = UserRoleType.USER;
        final var userID = UserID.unique();
        final var expectedErrorMessage = "User not found";
        final var expectedErrorCount = 1;

        Mockito.when(userGateway.getById(Mockito.eq(userID)))
                .thenReturn(Optional.empty());

        final var command = UpdateUserRoleCommand.with(
                userID,
                expectRole
        );

        final var notification = useCase.execute(command).getLeft();

        assertEquals(expectedErrorMessage, notification.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, notification.getErrors().size());
        verify(userGateway, times(0)).create(any());
    }

    @Test
    public void given_valid_password_when_gateway_throws_exception_should_return_exception() {
        final var expectedName = "User";
        final var expectedEmail = "user@email.com";
        final var oldRole = UserRoleType.ADMIN;
        final var expectedPassword = "P@ink1ller2";
        final var expectedRole = UserRoleType.USER;
        final var user = User.newUser(expectedName, expectedEmail, expectedPassword, oldRole);
        final var expectedErrorMessage = "Gateway Exception";
        final var expectedErrorCount = 1;

        final var command = UpdateUserRoleCommand.with(user.getId(), expectedRole);

        Mockito.when(userGateway.getById(Mockito.eq(user.getId())))
                .thenReturn(Optional.of(user));
        when(userGateway.updateRole(any(), any())).thenThrow(new IllegalArgumentException(expectedErrorMessage));

        final var notification = useCase.execute(command).getLeft();

        assertEquals(expectedErrorMessage, notification.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.getErrors().getFirst().message());

        verify(userGateway, Mockito.times(1))
                .getById(Mockito.eq(user.getId()));

        verify(userGateway, Mockito.times(1))
                .updateRole(user.getId(), expectedRole);
    }

}
