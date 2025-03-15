package com.app.budget.core.domain;

import com.app.budget.core.enums.FinancialRecordStatus;
import com.app.budget.core.exceptions.FinancialRecordException;
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
        Double predictedValue = 35.90;
        Double actualValue = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        FinancialRecordStatus status = FinancialRecordStatus.LATE;
        String description = "Going to the doctor";

        Expense expense = new Expense(id, categories, title, description, predictedValue, actualValue, dueDate, status);

        assertEquals(1, (long) expense.getId());
        assertEquals(2, expense.getCategories().size());
        assertEquals("Uber", expense.getTitle());
        assertEquals("Going to the doctor", expense.getDescription());
        assertEquals(35.90, expense.getPredictedValue(), 0.00);
        assertEquals("2024-02-01", expense.getDueDate().toString());
        assertEquals(FinancialRecordStatus.LATE, expense.getStatus());
        assertArrayEquals(categories.toArray(), expense.getCategories().toArray());
    }

    @Test
    public void shouldSetStatusToPendingIfStatusIsNull() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double predictedValue = 35.9563;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        Expense expense = new Expense(id, categories, title, null, predictedValue, null, dueDate, null);

        assertEquals(FinancialRecordStatus.PENDING, expense.getStatus());
    }

    @Test
    public void shouldBeAbleToUpdateStatus() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double predictedValue = 35.9563;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Expense expense = new Expense(id, categories, title, null, predictedValue, null, dueDate, null);

        expense.setStatus(FinancialRecordStatus.OK);

        assertEquals(FinancialRecordStatus.OK, expense.getStatus());

    }

    @Test
    public void shouldThrowExceptionWhenTryToUpdateStatusToNull() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double predictedValue = 35.9563;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        Expense expense = new Expense(id, categories, title, null, predictedValue, null, dueDate, null);

        FinancialRecordException exception = assertThrows(FinancialRecordException.class, () -> expense.setStatus(null));
        assertEquals("Status cannot be null", exception.getMessage());
    }


}
