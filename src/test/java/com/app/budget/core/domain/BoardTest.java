package com.app.budget.core.domain;

import com.app.budget.core.enums.TransactionStatus;
import com.app.budget.core.enums.UserRole;
import com.app.budget.core.exceptions.BoardException;
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
        Income income_1 = new Income(categories, "Uber", null, 30.00, 30.00, LocalDate.of(2024, 3, 1), TransactionStatus.OK);
        Income income_2 = new Income(categories, "Uber", null, 25.00, 25.00, LocalDate.of(2024, 3, 2), TransactionStatus.LATE);
        Income income_3 = new Income(categories, "Uber", null, 45.00, 45.00, LocalDate.of(2024, 3, 3), TransactionStatus.PENDING);

        Board board = new Board(user);
        board.setIncomes(List.of(income_1, income_2, income_3));

        assertEquals(100.00, board.getTotalPredictedIncomes(), 0.00);
    }

    @Test
    public void shouldBeAbleToGetExpensesTotal() {
        Category category_1 = new Category("Transṕortation");

        Set<Category> categories = Set.of(category_1);
        Expense expense_1 = new Expense(categories, "Uber", null, 30.00, 30.00, LocalDate.of(2024, 3, 1), TransactionStatus.PENDING);
        Expense expense_2 = new Expense(categories, "Uber", null, 25.00, 25.00, LocalDate.of(2024, 3, 2), TransactionStatus.PENDING);
        Expense expense_3 = new Expense(categories, "Uber", null, 45.00, 45.00, LocalDate.of(2024, 3, 3), TransactionStatus.PENDING);

        Board board = new Board(user);
        board.setExpenses(List.of(expense_1, expense_2, expense_3));

        assertEquals(100.00, board.getTotalPredictedExpenses(), 0.00);
    }

    @Test
    public void shouldBeAbleToGetActualIncomeTotal() {
        Category category_1 = new Category("Transṕortation");

        Set<Category> categories = Set.of(category_1);
        Income income_1 = new Income(categories, "Uber", null, 30.00, 30.00, LocalDate.of(2024, 3, 1), TransactionStatus.OK);
        Income income_2 = new Income(categories, "Uber", null, 25.00, 25.00, LocalDate.of(2024, 3, 2), TransactionStatus.LATE);
        Income income_3 = new Income(categories, "Uber", null, 45.00, 45.00, LocalDate.of(2024, 3, 3), TransactionStatus.PENDING);

        Board board = new Board(user);
        board.setIncomes(List.of(income_1, income_2, income_3));

        assertEquals(100.00, board.getTotalActualIncomes(), 0.00);
    }

    @Test
    public void shouldBeAbleToGetActualExpensesTotal() {
        Category category_1 = new Category("Transṕortation");

        Set<Category> categories = Set.of(category_1);
        Expense expense_1 = new Expense(categories, "Uber", null, 30.00, 30.00, LocalDate.of(2024, 3, 1), TransactionStatus.PENDING);
        Expense expense_2 = new Expense(categories, "Uber", null, 25.00, 25.00, LocalDate.of(2024, 3, 2), TransactionStatus.PENDING);
        Expense expense_3 = new Expense(categories, "Uber", null, 45.00, 45.00, LocalDate.of(2024, 3, 3), TransactionStatus.PENDING);

        Board board = new Board(user);
        board.setExpenses(List.of(expense_1, expense_2, expense_3));

        assertEquals(100.00, board.getTotalActualExpenses(), 0.00);
    }
}
