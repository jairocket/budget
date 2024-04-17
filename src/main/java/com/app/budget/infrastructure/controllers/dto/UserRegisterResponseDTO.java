package com.app.budget.infrastructure.controllers.dto;

import com.app.budget.core.enums.UserRole;

public record UserRegisterResponseDTO(Long id, String name, String email, UserRole role) {

}