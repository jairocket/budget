package com.app.budget.infrastructure.gateways;

import com.app.budget.core.domain.User;
import com.app.budget.infrastructure.controllers.dto.UserRegisterResponseDTO;
import com.app.budget.infrastructure.controllers.dto.UserResponseDTO;
import org.springframework.stereotype.Component;


@Component
public class UserDTOMapper {
    public UserRegisterResponseDTO toResponse(User user) {
        return new UserRegisterResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    public UserResponseDTO toUserDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().toString()
        );
    }
}
