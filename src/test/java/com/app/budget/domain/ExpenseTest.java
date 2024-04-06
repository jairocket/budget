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
        Set<Category> categories = Set.of(new Category("Transportation"), new Category("Health"));
        String title = "Uber";
        Double value = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        ExpenseStatus status = ExpenseStatus.LATE;
        String description = "Going to the doctor";

        Expense expense = new Expense(id, categories, title, description, value, dueDate, status);

        assertTrue(expense.getId() == 1);
        assertEquals(2, expense.getCategories().size());
        assertEquals("Uber", expense.getTitle());
        assertEquals("Going to the doctor", expense.getDescription());
        assertTrue(expense.getPredictedValue() == 35.90);
        assertEquals("2024-02-01", expense.getDueDate().toString());
        assertEquals(ExpenseStatus.LATE, expense.getStatus());
        assertArrayEquals(categories.toArray(), expense.getCategories().toArray());
    }

    @Test
    public void shouldSetStatusToPendingIfStatusIsNull() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double value = 35.9563;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        Expense expense = new Expense(id, categories, title, null, value, dueDate, null);

        assertEquals(expense.getStatus(), ExpenseStatus.PENDING);
    }

    @Test
    public void shouldThrowExceptionWhenTryToUpdateStatusToNull() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double value = 35.9563;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        Expense expense = new Expense(id, categories, title, null, value, dueDate, null);

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> expense.setStatus(null));
        assertEquals("Status cannot be null", exception.getMessage());
    }


}
