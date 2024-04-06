package com.app.budget.domain;

import com.app.budget.enums.IncomeStatus;
import com.app.budget.exceptions.FinancialOccurrenceException;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class IncomeTest {
    @Test
    public void shouldBeAbleToCreateAnIncome() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double value = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        Income income = new Income(id, categories, title, value, dueDate, null);

        assertTrue(income.getId() == 1);
        assertEquals(1, income.getCategories().size());
        assertEquals("Transportation", income.getCategories().stream().toList().get(0).getName());
        assertEquals("Uber", income.getTitle());
        assertTrue(income.getValue() == 35.90);
        assertEquals("2024-02-01", income.getDueDate().toString());
        assertEquals(IncomeStatus.PENDING, income.getStatus());
    }

    @Test
    public void shouldThrowExceptionWhenTryToUpdateStatusToNull() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double value = 35.9563;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        Income income = new Income(id, categories, title, value, dueDate, null);

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> income.setStatus(null));
        assertEquals("Status cannot be null", exception.getMessage());
    }

    @Test
    public void shouldBeAbleToUpdateIncomeStatus() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double value = 35.9563;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Income income = new Income(id, categories, title, value, dueDate, null);

        income.setStatus(IncomeStatus.RECEIVED);

        assertEquals(IncomeStatus.RECEIVED, income.getStatus());
    }
}
