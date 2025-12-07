package com.app.budget.application.user.updatePassword;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateUserPasswordUseCaseTest {
    @InjectMocks
    private DefaultUpdateUserPasswordUseCase useCase;

    @Mock
    private UserGateway userGateway;

    @Test
    public void given_a_valid_command_when_update_user_password_should_return_success() {
        final var expectedName = "User";
        final var expectedEmail = "user@email.com";
        final var oldPassword = "P@ink1ller";
        final var expectedPassword = "P@ink1ller2";
        final var expectedRole = UserRoleType.USER;
        final var user = User.newUser(expectedName, expectedEmail, oldPassword, expectedRole);

        final var command = UpdateUserPasswordCommand.with(user.getId(), expectedPassword);

        Mockito.when(userGateway.getById(Mockito.eq(user.getId())))
                .thenReturn(Optional.of(user));

        Mockito.when(userGateway.updatePassword(any(), any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(command).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(userGateway, Mockito.times(1))
                .getById(Mockito.eq(user.getId()));

        verify(userGateway, Mockito.times(1))
                .updatePassword(user.getId(), expectedPassword);
    }

    @Test
    public void given_invalid_password_when_update_password_should_return_domain_exception() {
        final var expectedName = "User";
        final var expectedEmail = "user@email.com";
        final var oldPassword = "P@ink1ller";
        final var expectedPassword = "Painkiller";
        final var expectedRole = UserRoleType.USER;
        final var user = User.newUser(expectedName, expectedEmail, oldPassword, expectedRole);

        final var expectedErrorMessage = "Inform a valid password";
        final var expectedErrorCount = 1;

        Mockito.when(userGateway.getById(Mockito.eq(user.getId())))
                .thenReturn(Optional.of(user));

        final var command = UpdateUserPasswordCommand.with(
                user.getId(),
                expectedPassword
        );

        final var notification = useCase.execute(command).getLeft();

        assertEquals(expectedErrorMessage, notification.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, notification.getErrors().size());
        verify(userGateway, times(0)).create(any());
    }

    @Test
    public void given_user_not_found_when_create_user_should_return_domain_exception() {
        final var expectedPassword = "Painkiller";
        final var userID = UserID.unique();
        final var expectedErrorMessage = "User not found";
        final var expectedErrorCount = 1;

        Mockito.when(userGateway.getById(Mockito.eq(userID)))
                .thenReturn(Optional.empty());

        final var command = UpdateUserPasswordCommand.with(
                userID,
                expectedPassword
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
        final var oldPassword = "P@ink1ller";
        final var expectedPassword = "P@ink1ller2";
        final var expectedRole = UserRoleType.USER;
        final var user = User.newUser(expectedName, expectedEmail, oldPassword, expectedRole);
        final var expectedErrorMessage = "Gateway Exception";
        final var expectedErrorCount = 1;

        final var command = UpdateUserPasswordCommand.with(user.getId(), expectedPassword);

        Mockito.when(userGateway.getById(Mockito.eq(user.getId())))
                .thenReturn(Optional.of(user));
        when(userGateway.updatePassword(any(), any())).thenThrow(new IllegalArgumentException(expectedErrorMessage));

        final var notification = useCase.execute(command).getLeft();

        assertEquals(expectedErrorMessage, notification.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.getErrors().getFirst().message());

        verify(userGateway, Mockito.times(1))
                .getById(Mockito.eq(user.getId()));

        verify(userGateway, Mockito.times(1))
                .updatePassword(user.getId(), expectedPassword);
    }

}
