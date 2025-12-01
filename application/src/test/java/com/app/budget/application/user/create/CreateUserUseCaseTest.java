package com.app.budget.application.user.create;


import com.app.budget.domain.entities.User.enums.UserRoleType;
import com.app.budget.domain.entities.User.gateway.UserGateway;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

public class CreateUserUseCaseTest {

    @Test
    public void given_valid_command_when_create_category_should_return_category_id() {
        final var expectedName = "User";
        final var expectedEmail = "user@email.com";
        final var expectedPassword = "P@ink1ller";
        final var expectedRole = UserRoleType.USER;

        final var command = CreateUserCommand.with(expectedName, expectedEmail, expectedPassword, expectedRole);

        final UserGateway userGateway = Mockito.mock(UserGateway.class);
        Mockito.when(userGateway.create(Mockito.any())).thenAnswer(returnsFirstArg());

        final var useCase = new DefaultCreateUserUseCase(userGateway);

        final var actualOutput = useCase.execute(command);

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        Mockito.verify(
                userGateway,
                Mockito.times(1)
        ).create(Mockito
                .argThat(
                        user -> Objects.equals(expectedName, user.getName()) &&
                                Objects.equals(expectedPassword, user.getPassword()) &&
                                Objects.nonNull(user.getId()) &&
                                Objects.equals(expectedEmail, user.getEmail()) &&
                                Objects.equals(expectedRole, user.getRole())
                )
        );
    }

}
