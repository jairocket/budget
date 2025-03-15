package com.app.budget.core.domain;

import com.app.budget.core.enums.FinancialRecordStatus;
import com.app.budget.core.exceptions.FinancialRecordException;
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
        Double predictedValue = 35.90;
        Double actualValue = 39.99;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        Income income = new Income(id, categories, title, null, predictedValue, actualValue, dueDate, null);

        assertTrue(income.getId() == 1);
        assertEquals(1, income.getCategories().size());
        assertEquals("Transportation", income.getCategories().stream().toList().get(0).getName());
        assertEquals("Uber", income.getTitle());
        assertEquals(35.90, income.getPredictedValue(), 0.00);
        assertEquals("2024-02-01", income.getDueDate().toString());
        assertEquals("", income.getDescription());
        assertEquals(FinancialRecordStatus.PENDING, income.getStatus());
    }

    @Test
    public void shouldThrowExceptionWhenTryToUpdateStatusToNull() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double predictedValue = 35.9563;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        Income income = new Income(id, categories, title, null, predictedValue, null, dueDate, null);

        FinancialRecordException exception = assertThrows(FinancialRecordException.class, () -> income.setStatus(null));
        assertEquals("Status cannot be null", exception.getMessage());
    }

    @Test
    public void shouldBeAbleToUpdateFinancialRecordStatus() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double value = 35.9563;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Income income = new Income(id, categories, title, null, value, null, dueDate, null);

        income.setStatus(FinancialRecordStatus.OK);

        assertEquals(FinancialRecordStatus.OK, income.getStatus());
    }
}
