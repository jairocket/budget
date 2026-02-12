package com.app.budget.domain.entities.User;

import com.app.budget.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class UserID extends Identifier {
    private final String value;

    private UserID(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static UserID unique() {
        return UserID.from(UUID.randomUUID());
    }

    public static UserID from(final String anId) {
        return new UserID(anId);
    }

    public static UserID from(final UUID andId) {
        return new UserID(andId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final UserID userID = (UserID) o;
        return Objects.equals(getValue(), userID.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
