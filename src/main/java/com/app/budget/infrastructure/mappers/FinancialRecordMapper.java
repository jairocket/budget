package com.app.budget.infrastructure.mappers;

import com.app.budget.core.domain.Expense;
import com.app.budget.core.domain.Income;
import com.app.budget.core.enums.FinancialRecordStatus;
import com.app.budget.core.enums.FinancialRecordType;
import com.app.budget.infrastructure.persistence.entities.FinancialRecordEntity;
import org.springframework.stereotype.Component;

@Component
public class FinancialRecordMapper {
    public FinancialRecordEntity toExpenseEntity(Expense expense) {
        return new FinancialRecordEntity(
                expense.getActualValue() * -1,
                expense.getCategories(),
                expense.getDescription(),
                expense.getDueDate(),
                expense.getId(),
                expense.getPredictedValue() * -1,
                FinancialRecordStatus.valueOf(expense.getStatus().toString()),
                expense.getTitle(),
                FinancialRecordType.EXPENSE
        );
    }

    public FinancialRecordEntity toIncomeEntity(Income income) {
        return new FinancialRecordEntity(
                income.getActualValue(),
                income.getCategories(),
                income.getDescription(),
                income.getDueDate(),
                income.getId(),
                income.getPredictedValue(),
                FinancialRecordStatus.valueOf(income.getStatus().toString()),
                income.getTitle(),
                FinancialRecordType.INCOME
        );
    }

    public Income toIncomeDomain(FinancialRecordEntity entity) {
        return new Income(
                entity.getId(),
                entity.getCategories(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPredictedValue(),
                entity.getActualValue(),
                entity.getDueDate(),
                FinancialRecordStatus.valueOf(entity.getStatus().toString())
        );
    }

    public Expense toExpenseDomain(FinancialRecordEntity entity) {

        return new Expense(
                entity.getId(),
                entity.getCategories(),
                entity.getTitle(),
                entity.getDescription(),
                Math.abs(entity.getPredictedValue()),
                Math.abs(entity.getActualValue()),
                entity.getDueDate(),
                FinancialRecordStatus.valueOf(entity.getStatus().toString())
        );
    }
}
