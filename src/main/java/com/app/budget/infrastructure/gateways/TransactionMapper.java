package com.app.budget.infrastructure.gateways;

import com.app.budget.core.domain.Expense;
import com.app.budget.core.domain.Income;
import com.app.budget.core.enums.TransactionStatus;
import com.app.budget.core.enums.TransactionType;
import com.app.budget.infrastructure.persistence.entities.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public TransactionEntity toExpenseEntity(Expense expense) {
        return new TransactionEntity(
                expense.getActualValue() * -1,
                expense.getCategories(),
                expense.getDescription(),
                expense.getDueDate(),
                expense.getId(),
                expense.getPredictedValue() * -1,
                TransactionStatus.valueOf(expense.getStatus().toString()),
                expense.getTitle(),
                TransactionType.EXPENSE
        );
    }

    public TransactionEntity toExpenseEntity(Income income) {
        return new TransactionEntity(
                income.getActualValue(),
                income.getCategories(),
                income.getDescription(),
                income.getDueDate(),
                income.getId(),
                income.getPredictedValue(),
                TransactionStatus.valueOf(income.getStatus().toString()),
                income.getTitle(),
                TransactionType.INCOME
        );
    }

    public Income toIncomeDomain(TransactionEntity entity) {
        return new Income(
                entity.getId(),
                entity.getCategories(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPredictedValue(),
                entity.getActualValue(),
                entity.getDueDate(),
                TransactionStatus.valueOf(entity.getStatus().toString())
        );
    }

    public Expense toExpenseDomain(TransactionEntity entity) {
        return new Expense(
                entity.getId(),
                entity.getCategories(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPredictedValue() * -1,
                entity.getActualValue() * -1,
                entity.getDueDate(),
                TransactionStatus.valueOf(entity.getStatus().toString())
        );
    }
}
