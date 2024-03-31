package com.app.budget.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IncomeTest {
    @Test
    public void shouldBeAbleToCreateAnIncome() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double value = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        Income expense = new Income(id, categories, title, value, dueDate);

        assertTrue(expense.getId() == 1);
        assertEquals(1, expense.getCategories().size());
        assertEquals("Transportation", expense.getCategories().stream().toList().get(0).getName());
        assertEquals("Uber", expense.getTitle());
        assertTrue(expense.getValue() == 35.90);
        assertEquals("2024-02-01", expense.getDueDate().toString());
    }
}
