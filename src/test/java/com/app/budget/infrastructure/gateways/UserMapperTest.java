package com.app.budget.infrastructure.gateways;

import com.app.budget.core.domain.User;
import com.app.budget.core.enums.UserRole;
import com.app.budget.infrastructure.persistence.entities.UserEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserMapperTest {
    private final UserMapper mapper = new UserMapper();

    @Test
    public void shouldBeAbleToConvertInEntity() {
        User userDomain = new User(1L, "Pedro", "pedro@email.com", "P1poc@85", UserRole.USER);
        UserEntity entity = mapper.toEntity(userDomain, userDomain.getPassword());

        assertEquals(1, entity.getId().longValue());
        assertEquals("Pedro", entity.getName());
        assertEquals("pedro@email.com", entity.getEmail());
        assertEquals("P1poc@85", entity.getPassword());
        assertEquals(UserRole.USER, entity.getRole());
    }

    @Test
    public void shouldBeAbleToConvertToDomain() {
        UserEntity entity = new UserEntity(1L, "Pedro", "pedro@email.com", "P1poc@85", UserRole.USER);
        User domain = mapper.toDomain(entity);

        assertEquals(1, domain.getId().longValue());
        assertEquals("Pedro", domain.getName());
        assertEquals("pedro@email.com", domain.getEmail());
        assertEquals("P1poc@85", domain.getPassword());
        assertEquals(UserRole.USER, domain.getRole());
    }

}
