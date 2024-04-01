package com.app.budget.domain;

import com.app.budget.enums.ExpenseStatus;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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


}
