package com.app.budget.application.user.create;

import com.app.budget.domain.entities.User.enums.UserRoleType;
import com.app.budget.domain.entities.User.gateway.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {

    @InjectMocks
    private DefaultCreateUserUseCase useCase;

    @Mock
    private UserGateway userGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(userGateway);
    }

    @Test
    public void given_valid_command_when_create_user_should_return_category_id() {
        final var expectedName = "User";
        final var expectedEmail = "user@email.com";
        final var expectedPassword = "P@ink1ller";
        final var expectedRole = UserRoleType.USER;

        final var command = CreateUserCommand.with(
                expectedName,
                expectedEmail,
                expectedPassword,
                expectedRole
        );

        when(userGateway.create(any())).thenAnswer(returnsFirstArg());
        final var actualOutput = useCase.execute(command).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(userGateway, Mockito.times(1))
                .create(Mockito.argThat(
                                user -> Objects.equals(expectedName, user.getName()) &&
                                        Objects.equals(expectedPassword, user.getPassword()) &&
                                        Objects.nonNull(user.getId()) &&
                                        Objects.equals(expectedEmail, user.getEmail()) &&
                                        Objects.equals(expectedRole, user.getRole())
                        )
                );
    }

    @Test
    public void given_invalid_name_when_create_user_should_return_domain_exception() {
        final var expectedName = "Us";
        final var expectedEmail = "user@email.com";
        final var expectedPassword = "P@ink1ller";
        final var expectedRole = UserRoleType.USER;
        final var expectedErrorMessage = "User name should have at least three characters";
        final var expectedErrorCount = 1;
        final var command = CreateUserCommand.with(
                expectedName,
                expectedEmail,
                expectedPassword,
                expectedRole
        );

        final var notification = useCase.execute(command).getLeft();

        assertEquals(expectedErrorMessage, notification.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, notification.getErrors().size());
        verify(userGateway, times(0)).create(any());
    }

    @Test
    public void given_valid_name_when_gateway_throws_exception_should_return_exception() {
        final var expectedName = "User";
        final var expectedEmail = "user@email.com";
        final var expectedPassword = "P@ink1ller";
        final var expectedRole = UserRoleType.USER;
        final var expectedErrorMessage = "Gateway Exception";
        final var expectedErrorCount = 1;
        final var command = CreateUserCommand.with(
                expectedName,
                expectedEmail,
                expectedPassword,
                expectedRole
        );
        when(userGateway.create(any())).thenThrow(new IllegalArgumentException(expectedErrorMessage));

        final var notification = useCase.execute(command).getLeft();

        assertEquals(expectedErrorMessage, notification.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.getErrors().getFirst().message());
        verify(userGateway, Mockito.times(1))
                .create(Mockito.argThat(
                                user -> Objects.equals(expectedName, user.getName()) &&
                                        Objects.equals(expectedPassword, user.getPassword()) &&
                                        Objects.nonNull(user.getId()) &&
                                        Objects.equals(expectedEmail, user.getEmail()) &&
                                        Objects.equals(expectedRole, user.getRole())
                        )
                );
    }
}
