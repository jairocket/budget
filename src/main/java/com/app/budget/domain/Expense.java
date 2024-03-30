package com.app.budget.domain;

import com.app.budget.enums.ExpenseStatus;

import java.time.LocalDate;
import java.util.Set;

public class Expense extends FinancialOccurrence {
    private ExpenseStatus status;

    public Expense(Long id, Set<Category> categories, String title, Double value, LocalDate dueDate, ExpenseStatus status) {
        super(id, categories, title, value, dueDate);
        if (status == null) status = ExpenseStatus.PENDING;

        this.status = status;
    }

    public Expense(Set<Category> categories, String title, Double value, LocalDate dueDate, ExpenseStatus status) {
        super(categories, title, value, dueDate);
        if (status == null) status = ExpenseStatus.PENDING;

        this.status = status;
    }

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }

}