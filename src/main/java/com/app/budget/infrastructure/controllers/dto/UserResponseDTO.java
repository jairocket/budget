package com.app.budget.infrastructure.controllers.dto;

import java.util.List;

public record UserResponseDTO(Long id, String name, String username, List<String> authorities) {
}
