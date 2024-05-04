package com.app.budget.core.domain;

import com.app.budget.core.enums.ExpenseStatus;
import com.app.budget.core.exceptions.FinancialOccurrenceException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public class Expense extends Event {
    private ExpenseStatus status;

    public Expense(Long id, Set<Category> categories, String title, String description, Double predictedValue, Double actualValue, LocalDate dueDate, ExpenseStatus status) {
        super(id, categories, title, description, predictedValue, actualValue, dueDate);
        if (status == null) status = ExpenseStatus.PENDING;

        this.status = status;
    }

    public Expense(Set<Category> categories, String title, String description, Double predictedValue, Double actualValue, LocalDate dueDate, ExpenseStatus status) {
        super(categories, title, description, predictedValue, actualValue, dueDate);
        if (status == null) status = ExpenseStatus.PENDING;

        this.status = status;
    }

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseStatus status) {
        status = Optional.ofNullable(status).orElseThrow(() -> new FinancialOccurrenceException("Status cannot be null"));
        this.status = status;
    }

}
