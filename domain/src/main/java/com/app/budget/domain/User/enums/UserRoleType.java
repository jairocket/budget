package com.app.budget.domain.User.enums;

public enum UserRoleType {
    ADMIN("admin"),
    USER("user");

    private String role;

    UserRoleType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
