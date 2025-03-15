package com.app.budget.infrastructure.mappers;

import com.app.budget.core.domain.Board;
import com.app.budget.core.domain.Expense;
import com.app.budget.core.domain.Income;
import com.app.budget.core.enums.FinancialRecordType;
import com.app.budget.infrastructure.persistence.entities.BoardEntity;
import com.app.budget.infrastructure.persistence.entities.FinancialRecordEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class BoardMapper {
    private final FinancialRecordMapper financialRecordMapper = new FinancialRecordMapper();

    public BoardEntity toEntity(Board domain) {
        Set<FinancialRecordEntity> financialRecordEntities = getFinancialRecordEntities(
                domain.getIncomes(),
                domain.getExpenses()
        );

        return new BoardEntity(
                domain.getId(),
                financialRecordEntities,
                domain.getUserId()
        );
    }

    public Board toDomain(BoardEntity entity) {


        Set<Expense> expenses = getExpenses(entity.getFinancialRecordsList());
        Set<Income> incomes = getIncomes(entity.getFinancialRecordsList());

        return new Board(
                entity.getUserId(),
                incomes,
                entity.getId(),
                expenses
        );
    }

    private Set<FinancialRecordEntity> getFinancialRecordEntities(Set<Income> incomes, Set<Expense> expenses) {
        Set<FinancialRecordEntity> financialRecordEntities = new HashSet<>();

        incomes.forEach(income -> financialRecordEntities.add(financialRecordMapper.toIncomeEntity(income)));

        expenses.forEach(expense -> financialRecordEntities.add(financialRecordMapper.toExpenseEntity(expense)));

        return financialRecordEntities;
    }

    private Set<Income> getIncomes(Set<FinancialRecordEntity> financialRecordEntities) {
        Set<Income> incomes = new HashSet<>();

        financialRecordEntities.forEach(financialRecordEntity -> {
            if (financialRecordEntity.getType().equals(FinancialRecordType.INCOME)) {
                incomes.add(financialRecordMapper.toIncomeDomain(financialRecordEntity));
            }
        });

        return incomes;
    }

    private Set<Expense> getExpenses(Set<FinancialRecordEntity> financialRecordEntities) {
        Set<Expense> expenses = new HashSet<>();

        financialRecordEntities.forEach(financialRecordEntity -> {
            if (financialRecordEntity.getType().equals(FinancialRecordType.EXPENSE)) {
                expenses.add(financialRecordMapper.toExpenseDomain(financialRecordEntity));
            }
        });

        return expenses;
    }
}
