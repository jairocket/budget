package com.app.budget.application.user.getByID;

import com.app.budget.domain.entities.User.User;
import com.app.budget.domain.entities.User.UserID;
import com.app.budget.domain.entities.User.enums.UserRoleType;
import com.app.budget.domain.entities.User.gateway.UserGateway;
import com.app.budget.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GetUserByIDUseCaseTest {
    @InjectMocks
    private DefaultGetUserByIdUseCase useCase;

    @Mock
    private UserGateway userGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(userGateway);
    }

    @Test
    public void given_valid_ID_when_call_get_user_by_id_should_return_user() {
        final var user = User.newUser("User", "user@email.com", "P@inK1ller", UserRoleType.USER);
        final var userId = user.getId();

        Mockito.when(userGateway.getById(Mockito.eq(userId))).thenReturn(Optional.of(user));

        final var actualUser = useCase.execute(userId);

        Assertions.assertEquals(user.getId(), actualUser.id());
        Assertions.assertEquals(user.getName(), actualUser.name());
        Assertions.assertEquals(user.getRole(), actualUser.role());
        Assertions.assertEquals(user.getEmail(), actualUser.email());
    }

    @Test
    public void given_invalid_ID_when_call_get_user_by_id_should_return_not_found() {
        final var id = UserID.from("123");
        final var expectedErrorMessage = "User with id 123 not found";

        Mockito.when(userGateway.getById(Mockito.eq(id))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> useCase.execute(id)
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
    }

    @Test
    public void given_valid_ID_when_gateway_throws_exception_should_return_exception() {
        final var expectedErrorMessage = "Gateway error";
        final var id = UserID.from("123");
        Mockito.when(userGateway.getById(Mockito.eq(id))).thenThrow(new IllegalAccessError(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(
                IllegalAccessError.class,
                () -> useCase.execute(id)
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}


