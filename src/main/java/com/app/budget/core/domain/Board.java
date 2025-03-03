package com.app.budget.core.domain;

import com.app.budget.core.exceptions.BoardException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Board {
    private final Long userId;
    private Long id;
    private Set<Income> incomes = new HashSet<>();
    private Set<Expense> expenses = new HashSet<>();

    public Board(Long userId, Set<Income> incomes, Long id, Set<Expense> expenses) {
        this.userId = userId;
        this.incomes = incomes;
        this.id = id;
        this.expenses = expenses;
    }

    public Board(Long id, Long userId) {
        userId = Optional.ofNullable(userId).orElseThrow(() -> new BoardException("User cannot be null"));

        this.id = id;
        this.userId = userId;
    }

    public Board(Long userId) {
        userId = Optional.ofNullable(userId).orElseThrow(() -> new BoardException("User cannot be null"));

        this.userId = userId;
    }

    public Set<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(Set<Income> incomes) {
        incomes = Optional.ofNullable(incomes).orElseThrow(() -> new BoardException("Incomes cannot be null"));

        this.incomes = incomes;
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        expenses = Optional.ofNullable(expenses).orElseThrow(() -> new BoardException("Expenses cannot be null"));

        this.expenses = expenses;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Double getTotalPredictedIncomes() {
        return incomes.stream().reduce(0.00, (subtotal, element) -> subtotal + element.getPredictedValue(), Double::sum);
    }

    public Double getTotalPredictedExpenses() {
        return expenses.stream().reduce(0.00, (subtotal, element) -> subtotal + element.getPredictedValue(), Double::sum);
    }

    public Double getTotalActualIncomes() {
        return incomes.stream().reduce(0.00, (subtotal, element) -> subtotal + element.getActualValue(), Double::sum);
    }

    public Double getTotalActualExpenses() {
        return expenses.stream().reduce(0.00, (subtotal, element) -> subtotal + element.getActualValue(), Double::sum);
    }
}
