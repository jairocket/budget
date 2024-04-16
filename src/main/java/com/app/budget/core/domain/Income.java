package com.app.budget.core.domain;

import com.app.budget.core.enums.IncomeStatus;
import com.app.budget.core.exceptions.FinancialOccurrenceException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public class Income extends FinancialOccurrence {
    IncomeStatus status;

    public Income(Long id, Set<Category> categories, String title, String description, Double predictedValue, Double actualValue, LocalDate dueDate, IncomeStatus status) {
        super(id, categories, title, description, predictedValue, actualValue, dueDate);
        status = Optional.ofNullable(status).orElse(IncomeStatus.PENDING);

        this.status = status;
    }

    public Income(Set<Category> categories, String title, String description, Double predictedValue, Double actualValue, LocalDate dueDate, IncomeStatus status) {
        super(categories, title, description, predictedValue, actualValue, dueDate);
        status = Optional.ofNullable(status).orElse(IncomeStatus.PENDING);

        this.status = status;
    }

    public IncomeStatus getStatus() {
        return status;
    }

    public void setStatus(IncomeStatus status) {
        status = Optional.ofNullable(status).orElseThrow(() -> new FinancialOccurrenceException("Status cannot be null"));
        this.status = status;
    }
}