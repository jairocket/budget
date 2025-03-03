package com.app.budget.core.domain;

import com.app.budget.core.enums.TransactionStatus;
import com.app.budget.core.exceptions.TransactionException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public class Expense extends Transaction {
    private TransactionStatus status;

    public Expense(Long id, Set<Category> categories, String title, String description, Double predictedValue, Double actualValue, LocalDate dueDate, TransactionStatus status) {
        super(id, categories, title, description, predictedValue, actualValue, dueDate);
        if (status == null) status = TransactionStatus.PENDING;

        this.status = status;
    }

    public Expense(Set<Category> categories, String title, String description, Double predictedValue, Double actualValue, LocalDate dueDate, TransactionStatus status) {
        super(categories, title, description, predictedValue, actualValue, dueDate);
        if (status == null) status = TransactionStatus.PENDING;

        this.status = status;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        status = Optional.ofNullable(status).orElseThrow(() -> new TransactionException("Status cannot be null"));
        this.status = status;
    }

}
