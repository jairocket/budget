package com.app.budget.domain;

import com.app.budget.enums.ExpenseStatus;
import com.app.budget.exceptions.FinancialOccurrenceException;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ExpenseTest {
    @Test
    public void shouldBeAbleToCreateExpense() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double value = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        ExpenseStatus status = ExpenseStatus.PENDING;

        Expense expense = new Expense(id, categories, title, value, dueDate, status);

        assertTrue(expense.getId() == 1);
        assertEquals(1, expense.getCategories().size());
        assertEquals("Transportation", expense.getCategories().stream().toList().get(0).getName());
        assertEquals("Uber", expense.getTitle());
        assertTrue(expense.getValue() == 35.90);
        assertEquals("2024-02-01", expense.getDueDate().toString());
        assertEquals(ExpenseStatus.PENDING, expense.getStatus());
    }

    @Test
    public void shouldValidateCategories() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        String title = "Uber";
        Double value = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        ExpenseStatus status = ExpenseStatus.PENDING;

        Expense expense = new Expense(id, categories, title, value, dueDate, status);

        assertTrue(expense.getId() == 1);
        assertEquals("Others", expense.getCategories().stream().toList().get(0).getName());
        assertEquals("Uber", expense.getTitle());
        assertTrue(expense.getValue() == 35.90);
        assertEquals("2024-02-01", expense.getDueDate().toString());
        assertEquals(ExpenseStatus.PENDING, expense.getStatus());
    }

    @Test
    public void shouldThrowExceptionIfCategoriesIsNull() {
        Double value = 35.90;
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        ExpenseStatus status = ExpenseStatus.PENDING;

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new Expense(null, title, value, dueDate, status));
        assertEquals("Categories cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfTitleIsNull() {
        Double value = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        ExpenseStatus status = ExpenseStatus.PENDING;
        Set<Category> categories = new HashSet<>();

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new Expense(categories, null, value, dueDate, status));
        assertEquals("Title cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfTitleHasLessThanThreeCharacters() {
        String title = "Ub";
        Double value = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        ExpenseStatus status = ExpenseStatus.PENDING;
        Set<Category> categories = new HashSet<>();

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new Expense(categories, title, value, dueDate, status));
        assertEquals("Title should have at least three characters", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfValueIsNull() {
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        ExpenseStatus status = ExpenseStatus.PENDING;
        Set<Category> categories = new HashSet<>();

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new Expense(categories, title, null, dueDate, status));
        assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfValueLowerThanZero() {
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        ExpenseStatus status = ExpenseStatus.PENDING;
        Set<Category> categories = new HashSet<>();
        Double value = -40.99;

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new Expense(categories, title, value, dueDate, status));
        assertEquals("Value should not be lower than zero", exception.getMessage());
    }

    @Test
    public void shouldFormatValueToTwoDecimalDigits() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double value = 35.9563;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        ExpenseStatus status = ExpenseStatus.PENDING;

        Expense expense = new Expense(id, categories, title, value, dueDate, status);

        assertTrue(expense.getValue() == 35.96);

    }

    @Test
    public void shouldSetStatusToPendingIfStatusIsNull() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double value = 35.9563;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        Expense expense = new Expense(id, categories, title, value, dueDate, null);

        assertEquals(expense.getStatus(), ExpenseStatus.PENDING);
    }

    @Test
    public void shouldThrowExceptionIfDueDateIsNull() {
        String title = "Uber";
        ExpenseStatus status = ExpenseStatus.PENDING;
        Set<Category> categories = new HashSet<>();
        Double value = -40.99;

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new Expense(categories, title, value, null, status));
        assertEquals("Value should not be lower than zero", exception.getMessage());
    }

}
