package com.app.budget.domain;

import com.app.budget.exceptions.BoardException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Board {
    private final User user;
    private Long id;
    private List<Income> incomes = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();

    public Board(Long id, User user) {
        user = Optional.ofNullable(user).orElseThrow(() -> new BoardException("User cannot be null"));

        this.id = id;
        this.user = user;
    }

    public Board(User user) {
        user = Optional.ofNullable(user).orElseThrow(() -> new BoardException("User cannot be null"));

        this.user = user;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Income> incomes) {
        incomes = Optional.ofNullable(incomes).orElseThrow(() -> new BoardException("Incomes cannot be null"));

        this.incomes = incomes;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        expenses = Optional.ofNullable(expenses).orElseThrow(() -> new BoardException("Expenses cannot be null"));

        this.expenses = expenses;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Double getTotalIncomes() {
        return incomes.stream().reduce(0.00, (subtotal, element) -> subtotal + element.getPredictedValue(), Double::sum);
    }

    public Double getTotalExpenses() {
        return expenses.stream().reduce(0.00, (subtotal, element) -> subtotal + element.getPredictedValue(), Double::sum);
    }

    public Double getBalance() {
        return getTotalIncomes() - getTotalExpenses();
    }
}
