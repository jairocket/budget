package com.app.budget.domain;

import com.app.budget.enums.ExpenseStatus;
import com.app.budget.exceptions.FinancialOccurrenceException;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class FinancialOccurrenceTest {

    @Test
    public void shouldValidateCategories() {
        Long id = 1L;
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);
        String title = "Uber";
        Double value = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        FinancialOccurrenceTestHelper expense = new FinancialOccurrenceTestHelper(id, categories, title, value, dueDate);

        assertTrue(expense.getId() == 1);
        assertEquals("Clothing", expense.getCategories().stream().toList().get(0).getName());
        assertEquals("Uber", expense.getTitle());
        assertTrue(expense.getValue() == 35.90);
        assertEquals("2024-02-01", expense.getDueDate().toString());
    }

    @Test
    public void shouldThrowExceptionIfCategoriesIsNull() {
        Long id = 1L;
        Double value = 35.90;
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new FinancialOccurrenceTestHelper(id, null, title, value, dueDate));
        assertEquals("Categories cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfCategoriesIsEmpty() {
        Long id = 1L;
        Double value = 35.90;
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Set<Category> categories = new HashSet<>();

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new FinancialOccurrenceTestHelper(id, categories, title, value, dueDate));
        assertEquals("Should inform at least one category", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfTitleIsNull() {
        Long id = 1L;
        Double value = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new FinancialOccurrenceTestHelper(id, categories, null, value, dueDate));
        assertEquals("Title cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfTitleHasLessThanThreeCharacters() {
        String title = "Ub";
        Double value = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        ExpenseStatus status = ExpenseStatus.PENDING;
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new Expense(categories, title, value, dueDate, status));
        assertEquals("Title should have at least three characters", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfValueIsNull() {
        Long id = 1L;
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new FinancialOccurrenceTestHelper(id, categories, title, null, dueDate));
        assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfValueLowerThanZero() {
        Long id = 1L;
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);
        Double value = -40.99;

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new FinancialOccurrenceTestHelper(id, categories, title, value, dueDate));
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
    public void shouldThrowExceptionIfDueDateIsNull() {
        Long id = 1L;
        String title = "Uber";
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);
        Double value = -40.99;

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new FinancialOccurrenceTestHelper(id, categories, title, value, null));
        assertEquals("Value should not be lower than zero", exception.getMessage());
    }
}

