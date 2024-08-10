package com.app.budget.infrastructure.gateways;

import com.app.budget.infrastructure.controllers.dto.UserRegisterResponseDTO;
import com.app.budget.infrastructure.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;


@Component
public class UserDTOMapper {
    public UserRegisterResponseDTO toResponse(UserEntity user) {
        return new UserRegisterResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
