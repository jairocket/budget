package com.app.budget.domain.entities.Board;

import com.app.budget.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class BoardID extends Identifier {
    private final String value;

    private BoardID(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static BoardID unique() {
        return BoardID.from(UUID.randomUUID());
    }

    public static BoardID from(final String anId) {
        return new BoardID(anId);
    }

    public static BoardID from(final UUID andId) {
        return new BoardID(andId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final BoardID boardID = (BoardID) o;
        return Objects.equals(getValue(), boardID.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
