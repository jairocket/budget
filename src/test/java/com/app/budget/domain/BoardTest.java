package com.app.budget.domain;

import com.app.budget.enums.ExpenseStatus;
import com.app.budget.enums.IncomeStatus;
import com.app.budget.enums.UserRole;
import com.app.budget.exceptions.BoardException;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class BoardTest {

    private final User user = new User(1L, "User", "user@user.com", "P@inK1ller", UserRole.USER);

    @Test
    public void shouldBeAbleToCreateBoard() {
        Board board = new Board(2L, user);

        assertTrue(board.getId() == 2);
        assertTrue(board.getUser().getId() == 1);
        assertEquals(0, board.getExpenses().size());
        assertEquals(0, board.getIncomes().size());
    }

    @Test
    public void shouldThrowExceptionWhenUserIsNull() {
        BoardException exception = assertThrows(BoardException.class, () -> new Board(null));

        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTryToUpdateIncomesToNull() {
        Board board = new Board(2L, user);
        BoardException exception = assertThrows(BoardException.class, () -> board.setIncomes(null));

        assertEquals("Incomes cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTryToUpdateExpensesToNull() {
        Board board = new Board(2L, user);
        BoardException exception = assertThrows(BoardException.class, () -> board.setExpenses(null));

        assertEquals("Expenses cannot be null", exception.getMessage());
    }

    @Test
    public void shouldBeAbleToGetIncomeTotal() {
        Category category_1 = new Category("Transṕortation");

        Set<Category> categories = Set.of(category_1);
        Income income_1 = new Income(categories, "Uber", null, 30.00, LocalDate.of(2024, 3, 1), IncomeStatus.RECEIVED);
        Income income_2 = new Income(categories, "Uber", null, 25.00, LocalDate.of(2024, 3, 2), IncomeStatus.LATE);
        Income income_3 = new Income(categories, "Uber", null, 45.00, LocalDate.of(2024, 3, 3), IncomeStatus.PENDING);

        Board board = new Board(user);
        board.setIncomes(List.of(income_1, income_2, income_3));

        assertTrue(board.getTotalIncomes() == 100.00);
    }

    @Test
    public void shouldBeAbleToGetExpensesTotal() {
        Category category_1 = new Category("Transṕortation");

        Set<Category> categories = Set.of(category_1);
        Expense expense_1 = new Expense(categories, "Uber", null, 30.00, LocalDate.of(2024, 3, 1), ExpenseStatus.PENDING);
        Expense expense_2 = new Expense(categories, "Uber", null, 25.00, LocalDate.of(2024, 3, 2), ExpenseStatus.PENDING);
        Expense expense_3 = new Expense(categories, "Uber", null, 45.00, LocalDate.of(2024, 3, 3), ExpenseStatus.PENDING);

        Board board = new Board(user);
        board.setExpenses(List.of(expense_1, expense_2, expense_3));

        assertTrue(board.getTotalExpenses() == 100.00);
    }

    @Test
    public void shouldBeAbleToGetBalance() {
        Category category_1 = new Category("Transṕortation");

        Set<Category> categories = Set.of(category_1);
        Expense expense_1 = new Expense(categories, "Uber", null, 10.00, LocalDate.of(2024, 3, 1), ExpenseStatus.PENDING);
        Expense expense_2 = new Expense(categories, "Uber", null, 25.00, LocalDate.of(2024, 3, 2), ExpenseStatus.PENDING);
        Income income_1 = new Income(categories, "Uber", null, 45.00, LocalDate.of(2024, 3, 3), IncomeStatus.PENDING);

        Board board = new Board(user);
        board.setIncomes(List.of(income_1));
        board.setExpenses(List.of(expense_1, expense_2));

        assertTrue(board.getBalance() == 10.00);
    }

}
