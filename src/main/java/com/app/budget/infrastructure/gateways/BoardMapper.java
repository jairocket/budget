package com.app.budget.infrastructure.gateways;

import com.app.budget.core.domain.Board;
import com.app.budget.core.domain.Expense;
import com.app.budget.core.domain.Income;
import com.app.budget.core.enums.TransactionType;
import com.app.budget.infrastructure.persistence.entities.BoardEntity;
import com.app.budget.infrastructure.persistence.entities.TransactionEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class BoardMapper {
    private final TransactionMapper transactionMapper = new TransactionMapper();

    public BoardEntity toEntity(Board domain) {
        Set<TransactionEntity> transactionEntities = getTransactions(
                domain.getIncomes(),
                domain.getExpenses()
        );

        return new BoardEntity(
                domain.getId(),
                transactionEntities,
                domain.getUserId()
        );
    }

    public Board toDomain(BoardEntity entity) {


        Set<Expense> expenses = getExpenses(entity.getTransactionsList());
        Set<Income> incomes = getIncomes(entity.getTransactionsList());

        return new Board(
                entity.getUserId(),
                incomes,
                entity.getId(),
                expenses
        );
    }

    private Set<TransactionEntity> getTransactions(Set<Income> incomes, Set<Expense> expenses) {
        Set<TransactionEntity> transactionEntities = new HashSet<>();

        incomes.forEach(income -> transactionEntities.add(transactionMapper.toIncomeEntity(income)));

        expenses.forEach(expense -> transactionEntities.add(transactionMapper.toExpenseEntity(expense)));

        return transactionEntities;
    }

    private Set<Income> getIncomes(Set<TransactionEntity> transactionEntities) {
        Set<Income> incomes = new HashSet<>();

        transactionEntities.forEach(transactionEntity -> {
            if (transactionEntity.getType().equals(TransactionType.INCOME)) {
                incomes.add(transactionMapper.toIncomeDomain(transactionEntity));
            }
        });

        return incomes;
    }

    private Set<Expense> getExpenses(Set<TransactionEntity> transactionEntities) {
        Set<Expense> expenses = new HashSet<>();

        transactionEntities.forEach(transactionEntity -> {
            if (transactionEntity.getType().equals(TransactionType.EXPENSE)) {
                expenses.add(transactionMapper.toExpenseDomain(transactionEntity));
            }
        });

        return expenses;
    }
}
