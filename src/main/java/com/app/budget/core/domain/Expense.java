package com.app.budget.core.domain;

import com.app.budget.core.enums.FinancialRecordStatus;
import com.app.budget.core.exceptions.FinancialRecordException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public class Expense extends FinancialRecord {
    private FinancialRecordStatus status;

    public Expense(Long id, Set<Category> categories, String title, String description, Double predictedValue, Double actualValue, LocalDate dueDate, FinancialRecordStatus status) {
        super(id, categories, title, description, predictedValue, actualValue, dueDate);
        if (status == null) status = FinancialRecordStatus.PENDING;

        this.status = status;
    }

    public Expense(Set<Category> categories, String title, String description, Double predictedValue, Double actualValue, LocalDate dueDate, FinancialRecordStatus status) {
        super(categories, title, description, predictedValue, actualValue, dueDate);
        if (status == null) status = FinancialRecordStatus.PENDING;

        this.status = status;
    }

    public FinancialRecordStatus getStatus() {
        return status;
    }

    public void setStatus(FinancialRecordStatus status) {
        status = Optional.ofNullable(status).orElseThrow(() -> new FinancialRecordException("Status cannot be null"));
        this.status = status;
    }

}
