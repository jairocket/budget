package com.app.budget.dto;

import com.app.budget.enums.UserRole;

public record UserRegisterDTO(String name, String email, String password, UserRole role) {

}
