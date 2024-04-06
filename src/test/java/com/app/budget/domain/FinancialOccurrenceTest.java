package com.app.budget.domain;

import com.app.budget.enums.ExpenseStatus;
import com.app.budget.exceptions.FinancialOccurrenceException;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

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
        Double predictedValue = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        FinancialOccurrenceTestHelper expense = new FinancialOccurrenceTestHelper(id, categories, title, null, predictedValue, null, dueDate);

        assertTrue(expense.getId() == 1);
        assertEquals("Clothing", expense.getCategories().stream().toList().get(0).getName());
        assertEquals("Uber", expense.getTitle());
        assertTrue(expense.getPredictedValue() == 35.90);
        assertEquals("2024-02-01", expense.getDueDate().toString());
    }

    @Test
    public void shouldThrowExceptionIfCategoriesIsNull() {
        Long id = 1L;
        Double value = 35.90;
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new FinancialOccurrenceTestHelper(id, null, title, null, value, null, dueDate));
        assertEquals("Categories cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfCategoriesIsEmpty() {
        Long id = 1L;
        Double value = 35.90;
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Set<Category> categories = new HashSet<>();

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new FinancialOccurrenceTestHelper(id, categories, title, null, value, null, dueDate));
        assertEquals("Should inform at least one category", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfTitleIsNull() {
        Long id = 1L;
        Double value = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new FinancialOccurrenceTestHelper(id, categories, null, null, value, null, dueDate));
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

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new Expense(categories, title, null, value, null, dueDate, status));
        assertEquals("Title should have at least three characters", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfTitleHasMoreThanFortyFiveCharacters() {
        String longTitle = "qwertyuiopasdfghjklzxcvbnmmnbvcxzlkjhgfdsapoiuytrewq";
        Double value = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        ExpenseStatus status = ExpenseStatus.PENDING;
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new Expense(categories, longTitle, null, value, null, dueDate, status));
        assertEquals("Title should have less than forty-five characters", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfDescriptionLengthIsBiggerThan256() {
        Long id = 1L;
        Double value = 35.90;
        String title = "Uber";
        String longDescription = RandomStringUtils.random(257);
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new FinancialOccurrenceTestHelper(id, categories, title, longDescription, value, null, dueDate));
        assertEquals("Description should have less than 256 characters", exception.getMessage());
    }

    @Test
    public void shouldUpdateValueToZeroIfItIsNull() {
        Long id = 1L;
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);

        var financialOccurrence = new FinancialOccurrenceTestHelper(id, categories, title, null, null, null, dueDate);

        assertEquals(0.00, financialOccurrence.getActualValue(), 0.0);
        assertEquals(0.00, financialOccurrence.getPredictedValue(), 0.0);

    }

    @Test
    public void shouldThrowExceptionIfValueLowerThanZero() {
        Long id = 1L;
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);
        Double value = -40.99;

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new FinancialOccurrenceTestHelper(id, categories, title, null, value, null, dueDate));
        assertEquals("Value should not be lower than zero", exception.getMessage());
    }

    @Test
    public void shouldFormatActualValueToTwoDecimalDigits() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double predictedValue = 35.9563;
        Double actualValue = 35.9563;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        ExpenseStatus status = ExpenseStatus.PENDING;

        Expense expense = new Expense(id, categories, title, null, predictedValue, actualValue, dueDate, status);

        assertTrue(expense.getActualValue() == 35.96);

    }

    @Test
    public void shouldFormatPredictedValueToTwoDecimalDigits() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double value = 35.9563;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        ExpenseStatus status = ExpenseStatus.PENDING;

        Expense expense = new Expense(id, categories, title, null, value, null, dueDate, status);

        assertTrue(expense.getPredictedValue() == 35.96);

    }


    @Test
    public void shouldThrowExceptionIfValueIsLowerThanZero() {
        Long id = 1L;
        String title = "Uber";
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);
        Double value = -40.99;

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new FinancialOccurrenceTestHelper(id, categories, title, null, value, null, null));
        assertEquals("Value should not be lower than zero", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfDueDateIsNull() {
        Long id = 1L;
        String title = "Uber";
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);
        Double value = 40.99;

        FinancialOccurrenceException exception = assertThrows(FinancialOccurrenceException.class, () -> new FinancialOccurrenceTestHelper(id, categories, title, null, value, null, null));
        assertEquals("Due date should not be null", exception.getMessage());
    }
}

