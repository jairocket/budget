package com.app.budget.infrastructure.controllers.dto;

import com.app.budget.core.enums.UserRole;

public record UserRegisterDTO(String name, String email, String password, UserRole role) {

}
