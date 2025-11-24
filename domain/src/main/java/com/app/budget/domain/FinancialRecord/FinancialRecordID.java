package com.app.budget.domain.FinancialRecord;

import com.app.budget.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class FinancialRecordID extends Identifier {
    private final String value;

    private FinancialRecordID(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static FinancialRecordID unique() {
        return FinancialRecordID.from(UUID.randomUUID());
    }

    public static FinancialRecordID from(final String anId) {
        return new FinancialRecordID(anId);
    }

    public static FinancialRecordID from(final UUID andId) {
        return new FinancialRecordID(andId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FinancialRecordID that = (FinancialRecordID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
