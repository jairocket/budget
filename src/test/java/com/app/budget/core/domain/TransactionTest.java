package com.app.budget.core.domain;

import com.app.budget.core.enums.TransactionStatus;
import com.app.budget.core.exceptions.TransactionException;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TransactionTest {

    @Test
    public void shouldValidateCategories() {
        Long id = 1L;
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);
        String title = "Uber";
        Double predictedValue = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        TransactionTestHelper expense = new TransactionTestHelper(id, categories, title, null, predictedValue, null, dueDate);

        assertTrue(expense.getId() == 1);
        assertEquals("Clothing", expense.getCategories().stream().toList().get(0).getName());
        assertEquals("Uber", expense.getTitle());
        assertEquals(35.90, expense.getPredictedValue(), 0.0);
        assertEquals("2024-02-01", expense.getDueDate().toString());
    }

    @Test
    public void shouldThrowExceptionIfCategoriesIsNull() {
        Long id = 1L;
        Double value = 35.90;
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        TransactionException exception = assertThrows(TransactionException.class, () -> new TransactionTestHelper(id, null, title, null, value, null, dueDate));
        assertEquals("Categories cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfCategoriesIsEmpty() {
        Long id = 1L;
        Double value = 35.90;
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Set<Category> categories = new HashSet<>();

        TransactionException exception = assertThrows(TransactionException.class, () -> new TransactionTestHelper(id, categories, title, null, value, null, dueDate));
        assertEquals("Should inform at least one category", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfTitleIsNull() {
        Long id = 1L;
        Double value = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);

        TransactionException exception = assertThrows(TransactionException.class, () -> new TransactionTestHelper(id, categories, null, null, value, null, dueDate));
        assertEquals("Title cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfTitleHasLessThanThreeCharacters() {
        String title = "Ub";
        Double value = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        TransactionStatus status = TransactionStatus.PENDING;
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);

        TransactionException exception = assertThrows(TransactionException.class, () -> new Expense(categories, title, null, value, null, dueDate, status));
        assertEquals("Title should have at least three characters", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfTitleHasMoreThanFortyFiveCharacters() {
        String longTitle = "qwertyuiopasdfghjklzxcvbnmmnbvcxzlkjhgfdsapoiuytrewq";
        Double value = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        TransactionStatus status = TransactionStatus.PENDING;
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);

        TransactionException exception = assertThrows(TransactionException.class, () -> new Expense(categories, longTitle, null, value, null, dueDate, status));
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

        TransactionException exception = assertThrows(TransactionException.class, () -> new TransactionTestHelper(id, categories, title, longDescription, value, null, dueDate));
        assertEquals("Description should have less than 256 characters", exception.getMessage());
    }

    @Test
    public void shouldUpdateValueToZeroIfItIsNull() {
        Long id = 1L;
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);

        var event = new TransactionTestHelper(id, categories, title, null, null, null, dueDate);

        assertEquals(0.00, event.getActualValue(), 0.0);
        assertEquals(0.00, event.getPredictedValue(), 0.0);

    }

    @Test
    public void shouldThrowExceptionIfValueLowerThanZero() {
        Long id = 1L;
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);
        Double value = -40.99;

        TransactionException exception = assertThrows(TransactionException.class, () -> new TransactionTestHelper(id, categories, title, null, value, null, dueDate));
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
        TransactionStatus status = TransactionStatus.PENDING;

        Expense expense = new Expense(id, categories, title, null, predictedValue, actualValue, dueDate, status);

        assertEquals(35.96, expense.getActualValue(), 0.00);

    }

    @Test
    public void shouldFormatPredictedValueToTwoDecimalDigits() {
        Long id = 1L;
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("Transportation"));
        String title = "Uber";
        Double value = 35.9563;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        TransactionStatus status = TransactionStatus.PENDING;

        Expense expense = new Expense(id, categories, title, null, value, null, dueDate, status);

        assertEquals(35.96, expense.getPredictedValue(), 0.00);

    }


    @Test
    public void shouldThrowExceptionIfValueIsLowerThanZero() {
        Long id = 1L;
        String title = "Uber";
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);
        Double value = -40.99;

        TransactionException exception = assertThrows(TransactionException.class, () -> new TransactionTestHelper(id, categories, title, null, value, null, null));
        assertEquals("Value should not be lower than zero", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfDueDateIsNull() {
        Long id = 1L;
        String title = "Uber";
        Category category = new Category("Clothing");
        Set<Category> categories = Set.of(category);
        Double value = 40.99;

        TransactionException exception = assertThrows(TransactionException.class, () -> new TransactionTestHelper(id, categories, title, null, value, null, null));
        assertEquals("Due date should not be null", exception.getMessage());
    }

    @Test
    public void shouldBeAbleToUpdateProperties() {
        Long id = 1L;
        Category category = new Category("Clothing");
        Set<Category> categories = new HashSet<>(Set.of(category));
        String title = "Uber";
        Double predictedValue = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        TransactionTestHelper expense = new TransactionTestHelper(id, categories, title, null, predictedValue, null, dueDate);
        categories.add(new Category("Transportation"));

        expense.setTitle("Taxi");
        expense.setCategories(categories);
        expense.setPredictedValue(38.99);
        expense.setActualValue(41.50);
        expense.setDescription("Going to buy clothing");
        expense.setDueDate(LocalDate.of(2024, 2, 5));

        assertEquals(2, expense.getCategories().size());
        assertEquals("Taxi", expense.getTitle());
        assertEquals("Going to buy clothing", expense.getDescription());
        assertEquals(38.99, expense.getPredictedValue(), 0.00);
        assertEquals(41.50, expense.getActualValue(), 0.00);
        assertEquals("2024-02-05", expense.getDueDate().toString());
        assertArrayEquals(categories.toArray(), expense.getCategories().toArray());
    }

    @Test
    public void shouldThrowExceptionIfUpdateDueDateToNull() {
        Long id = 1L;
        Category category = new Category("Clothing");
        Set<Category> categories = new HashSet<>(Set.of(category));
        String title = "Uber";
        Double predictedValue = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        TransactionTestHelper expense = new TransactionTestHelper(id, categories, title, null, predictedValue, null, dueDate);

        TransactionException exception = assertThrows(TransactionException.class, () -> expense.setDueDate(null));
        assertEquals("Due date should not be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfUpdateTitleToNull() {
        Long id = 1L;
        Category category = new Category("Clothing");
        Set<Category> categories = new HashSet<>(Set.of(category));
        String title = "Uber";
        Double predictedValue = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        TransactionTestHelper expense = new TransactionTestHelper(id, categories, title, null, predictedValue, null, dueDate);

        TransactionException exception = assertThrows(TransactionException.class, () -> expense.setTitle(null));
        assertEquals("Title cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfUpdateDescriptionToMoreThan256Characters() {
        Long id = 1L;
        Category category = new Category("Clothing");
        Set<Category> categories = new HashSet<>(Set.of(category));
        String title = "Uber";
        Double predictedValue = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        String longDescription = RandomStringUtils.randomAlphabetic(257);

        TransactionTestHelper expense = new TransactionTestHelper(id, categories, title, null, predictedValue, null, dueDate);

        TransactionException exception = assertThrows(TransactionException.class, () -> expense.setDescription(longDescription));
        assertEquals("Description should have less than 256 characters", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfUpdatePredictedValueToLowerThanZero() {
        Long id = 1L;
        Category category = new Category("Clothing");
        Set<Category> categories = new HashSet<>(Set.of(category));
        String title = "Uber";
        Double predictedValue = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        TransactionTestHelper expense = new TransactionTestHelper(id, categories, title, null, predictedValue, null, dueDate);

        TransactionException exception = assertThrows(TransactionException.class, () -> expense.setPredictedValue(-100.99));
        assertEquals("Value should not be lower than zero", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfUpdateActualValueToLowerThanZero() {
        Long id = 1L;
        Category category = new Category("Clothing");
        Set<Category> categories = new HashSet<>(Set.of(category));
        String title = "Uber";
        Double predictedValue = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        TransactionTestHelper expense = new TransactionTestHelper(id, categories, title, null, predictedValue, null, dueDate);

        TransactionException exception = assertThrows(TransactionException.class, () -> expense.setActualValue(-100.99));
        assertEquals("Value should not be lower than zero", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfUpdateCategoriesToNull() {
        Long id = 1L;
        Category category = new Category("Clothing");
        Set<Category> categories = new HashSet<>(Set.of(category));
        String title = "Uber";
        Double predictedValue = 35.90;
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        TransactionTestHelper expense = new TransactionTestHelper(id, categories, title, null, predictedValue, null, dueDate);

        TransactionException exception = assertThrows(TransactionException.class, () -> expense.setCategories(null));
        assertEquals("Categories cannot be null", exception.getMessage());
    }

}

