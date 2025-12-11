package com.app.budget.application.user.updateName;

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
public class UpdateUserNameUseCaseTest {
    @InjectMocks
    private DefaultUpdateUserNameUseCase useCase;

    @Mock
    private UserGateway userGateway;

    @Test
    public void given_a_valid_command_when_update_user_name_should_return_success() {
        final var oldName = "User";
        final var expectedName = "User Novo";
        final var expectedEmail = "user@email.com";
        final var expectedPassword = "P@ink1ller";
        final var expectedRole = UserRoleType.USER;
        final var user = User.newUser(oldName, expectedEmail, expectedPassword, expectedRole);

        final var command = UpdateUserNameCommand.with(user.getId(), expectedName);

        Mockito.when(userGateway.getById(Mockito.eq(user.getId())))
                .thenReturn(Optional.of(user));

        Mockito.when(userGateway.updateName(any(), any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(command).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(userGateway, Mockito.times(1))
                .getById(Mockito.eq(user.getId()));

        verify(userGateway, Mockito.times(1))
                .updateName(user.getId(), expectedName);
    }

    @Test
    public void given_invalid_password_when_update_name_should_return_domain_exception() {
        final var oldName = "User";
        final var expectedName = "Us";
        final var expectedEmail = "user@email.com";
        final var expectedPassword = "P@ink1ller";
        final var expectedRole = UserRoleType.USER;
        final var user = User.newUser(oldName, expectedEmail, expectedPassword, expectedRole);

        final var expectedErrorMessage = "User name should have at least three characters";
        final var expectedErrorCount = 1;

        Mockito.when(userGateway.getById(Mockito.eq(user.getId())))
                .thenReturn(Optional.of(user));

        final var command = UpdateUserNameCommand.with(
                user.getId(),
                expectedName
        );

        final var notification = useCase.execute(command).getLeft();

        assertEquals(expectedErrorMessage, notification.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, notification.getErrors().size());
        verify(userGateway, times(0)).updateName(any(), any());
    }

    @Test
    public void given_user_not_found_when_create_user_should_return_domain_exception() {
        final var expectedPassword = "Painkiller";
        final var userID = UserID.unique();
        final var expectedErrorMessage = "User not found";
        final var expectedErrorCount = 1;

        Mockito.when(userGateway.getById(Mockito.eq(userID)))
                .thenReturn(Optional.empty());

        final var command = UpdateUserNameCommand.with(
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
        final var expectedName = "Use";
        final var expectedEmail = "user@email.com";
        final var oldName = "User";
        final var expectedPassword = "P@ink1ller2";
        final var expectedRole = UserRoleType.USER;
        final var user = User.newUser(oldName, expectedEmail, expectedPassword, expectedRole);
        final var expectedErrorMessage = "Gateway Exception";
        final var expectedErrorCount = 1;

        final var command = UpdateUserNameCommand.with(user.getId(), expectedName);

        Mockito.when(userGateway.getById(Mockito.eq(user.getId())))
                .thenReturn(Optional.of(user));
        when(userGateway.updateName(any(), any())).thenThrow(new IllegalArgumentException(expectedErrorMessage));

        final var notification = useCase.execute(command).getLeft();

        assertEquals(expectedErrorMessage, notification.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.getErrors().getFirst().message());

        verify(userGateway, Mockito.times(1))
                .getById(Mockito.eq(user.getId()));

        verify(userGateway, Mockito.times(1))
                .updateName(user.getId(), expectedName);
    }
}




