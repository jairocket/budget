package com.app.budget.infrastructure.gateways;

import com.app.budget.core.domain.User;
import com.app.budget.infrastructure.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMapper {

    public UserEntity toEntity(User user, String encryptedPassword) {
        return new UserEntity(user.getId(), user.getName(), user.getEmail(), encryptedPassword, user.getRole());
    }

    public User toDomain(UserEntity entity) {
        Long id = Optional.ofNullable(entity.getId()).orElse(null);
        return new User(id, entity.getName(), entity.getEmail(), entity.getPassword(), entity.getRole());
    }

}
