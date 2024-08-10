package com.app.budget.infrastructure.gateways;

import com.app.budget.core.domain.User;
import com.app.budget.core.enums.UserRole;
import com.app.budget.infrastructure.controllers.dto.UserRegisterDTO;
import com.app.budget.infrastructure.controllers.dto.UserRegisterResponseDTO;
import com.app.budget.infrastructure.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;


@Component
public class UserDTOMapper {
    public User toDomain(UserRegisterDTO dto, UserRole role) {
        return new User(dto.name(), dto.email(), dto.password(), role);
    }

    public UserRegisterResponseDTO toResponse(UserEntity user) {
        return new UserRegisterResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
