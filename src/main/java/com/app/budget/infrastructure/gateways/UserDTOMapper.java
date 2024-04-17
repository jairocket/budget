package com.app.budget.infrastructure.gateways;

import com.app.budget.core.domain.User;
import com.app.budget.infrastructure.controllers.dto.UserRegisterDTO;
import com.app.budget.infrastructure.controllers.dto.UserRegisterResponseDTO;
import org.springframework.stereotype.Component;


@Component
public class UserDTOMapper {
    public User toDomain(UserRegisterDTO dto) {
        return new User(dto.name(), dto.email(), dto.password(), dto.role());
    }

    public UserRegisterResponseDTO toResponse(User user) {
        return new UserRegisterResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
