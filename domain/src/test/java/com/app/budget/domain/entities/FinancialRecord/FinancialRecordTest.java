package com.app.budget.domain.entities.FinancialRecord;

import com.app.budget.domain.entities.Category.Category;
import com.app.budget.domain.entities.FinancialRecord.enums.FinancialRecordStatus;
import com.app.budget.domain.entities.FinancialRecord.enums.FinancialRecordType;
import com.app.budget.domain.exceptions.DomainException;
import com.app.budget.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FinancialRecordTest {
    @Test
    public void given_proper_params_should_be_able_to_create_financial_record() {
        var category = Category.newCategory("Clothing");
        Set<Category> categories = Set.of(category);
        String title = "Uber";
        var predictedValue = BigDecimal.valueOf(35.90);
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        FinancialRecord expense = FinancialRecord.newFinancialRecord(
                title,
                null,
                categories,
                predictedValue,
                null,
                dueDate,
                null,
                FinancialRecordType.EXPENSE
        );

        assertNotNull(expense.getId());
        assertEquals("Clothing", expense.getCategories().stream().toList().getFirst().getName());
        assertEquals("Uber", expense.getTitle());
        assertEquals(BigDecimal.valueOf(35.90).setScale(2, RoundingMode.HALF_UP), expense.getPredictedValue());
        assertEquals("2024-02-01", expense.getDueDate().toString());
        assertEquals(FinancialRecordStatus.PENDING, expense.getStatus());

        assertDoesNotThrow(() -> expense.validate(new ThrowsValidationHandler()));
    }

    @Test
    public void given_null_category_when_validating_should_throw_exception() {
        var value = BigDecimal.valueOf(35.90);
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);

        var expense = FinancialRecord.newFinancialRecord(
                title,
                null,
                null,
                value,
                null,
                dueDate,
                null,
                FinancialRecordType.EXPENSE
        );

        DomainException exception = assertThrows(DomainException.class, () -> expense.validate(new ThrowsValidationHandler()));
        assertEquals("Should inform at least one category", exception.getErrors().getFirst().message());
    }

    @Test
    public void given_empty_category_list_when_validating_should_throw_exception() {
        var value = BigDecimal.valueOf(35.90);
        var title = "Uber";
        var dueDate = LocalDate.of(2024, 2, 1);

        var expense = FinancialRecord.newFinancialRecord(
                title,
                null,
                Set.of(),
                value,
                null,
                dueDate,
                null,
                FinancialRecordType.EXPENSE
        );

        DomainException exception = assertThrows(DomainException.class, () -> expense.validate(new ThrowsValidationHandler()));
        assertEquals("Should inform at least one category", exception.getErrors().getFirst().message());
    }

    @Test
    public void given_null_title_when_validating_should_throw_exception() {
        var value = BigDecimal.valueOf(35.90);
        var dueDate = LocalDate.of(2024, 2, 1);
        var category = Category.newCategory("Clothing");

        var expense = FinancialRecord.newFinancialRecord(
                null,
                null,
                Set.of(category),
                value,
                null,
                dueDate,
                null,
                FinancialRecordType.EXPENSE
        );

        DomainException exception = assertThrows(DomainException.class, () -> expense.validate(new ThrowsValidationHandler()));
        assertEquals("Title cannot be null", exception.getErrors().getFirst().message());
    }

    @Test
    public void given_title_shorter_than_three_characters_when_validating_should_throw_exception() {
        var title = "Ub";
        var value = BigDecimal.valueOf(35.90);
        var dueDate = LocalDate.of(2024, 2, 1);
        var category = Category.newCategory("Clothing");

        var financialRecord = FinancialRecord.newFinancialRecord(
                title,
                null,
                Set.of(category),
                value,
                null,
                dueDate,
                FinancialRecordStatus.PENDING,
                FinancialRecordType.EXPENSE
        );

        DomainException exception = assertThrows(DomainException.class, () -> financialRecord.validate(new ThrowsValidationHandler()));
        assertEquals("Title should have at least three characters", exception.getErrors().getFirst().message());
    }

    @Test
    public void given_title_longer_than_forty_five_characters_when_validating_should_throw_exception() {
        var longTitle = "qwertyuiopasdfghjklzxcvbnmmnbvcxzlkjhgfdsapoiuytrewq";
        var value = BigDecimal.valueOf(35.90);
        var dueDate = LocalDate.of(2024, 2, 1);
        var category = Category.newCategory("Clothing");

        var expense = FinancialRecord.newFinancialRecord(
                longTitle,
                null,
                Set.of(category),
                value,
                null,
                dueDate,
                FinancialRecordStatus.PENDING,
                FinancialRecordType.EXPENSE
        );

        DomainException exception = assertThrows(DomainException.class, () -> expense.validate(new ThrowsValidationHandler()));
        assertEquals("Title should have less than forty-five characters", exception.getErrors().getFirst().message());
    }

    @Test
    public void given_description_longer_than_256_characters_when_validating_should_throw_exception() {
        var value = BigDecimal.valueOf(35.90);
        String title = "Uber";
        String longDescription = "UberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUberUber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Category category = Category.newCategory("Clothing");

        var expense = FinancialRecord.newFinancialRecord(
                title,
                longDescription,
                Set.of(category),
                value,
                null,
                dueDate,
                FinancialRecordStatus.PENDING,
                FinancialRecordType.EXPENSE
        );

        DomainException exception = assertThrows(DomainException.class, () -> expense.validate(new ThrowsValidationHandler()));
        assertEquals("Description should have less than 256 characters", exception.getErrors().getFirst().message());
    }

    @Test
    public void given_null_value_when_creating_should_set_value_to_zero() {
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Category category = Category.newCategory("Clothing");
        Set<Category> categories = Set.of(category);

        var event = FinancialRecord.newFinancialRecord(title, null, categories, null, null, dueDate, null, FinancialRecordType.INCOME);

        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), event.getActualValue());
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), event.getPredictedValue());
    }

    @Test
    public void given_value_lower_than_zero_when_validating_should_throw_exception() {
        String title = "Uber";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Category category = Category.newCategory("Clothing");
        var value = BigDecimal.valueOf(-40.99);

        var expense = FinancialRecord.newFinancialRecord(
                title,
                null,
                Set.of(category),
                value,
                null,
                dueDate,
                FinancialRecordStatus.PENDING,
                FinancialRecordType.EXPENSE
        );

        DomainException exception = assertThrows(DomainException.class, () -> expense.validate(new ThrowsValidationHandler()));
        assertEquals("Value should not be lower than zero", exception.getErrors().getFirst().message());
    }

    @Test
    public void given_financial_record_when_creating_should_format_actual_value_to_two_decimal_digits() {
        Set<Category> categories = new HashSet<>();
        categories.add(Category.newCategory("Transportation"));
        String title = "Uber";
        var predictedValue = BigDecimal.valueOf(35.9563);
        var actualValue = BigDecimal.valueOf(35.9563);
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        FinancialRecordStatus status = FinancialRecordStatus.PENDING;

        var expense = FinancialRecord.newFinancialRecord(
                title,
                null,
                categories,
                predictedValue,
                actualValue,
                dueDate,
                status,
                FinancialRecordType.EXPENSE
        );

        assertEquals(BigDecimal.valueOf(35.96), expense.getActualValue());
        assertEquals(BigDecimal.valueOf(35.96), expense.getPredictedValue());

    }

    @Test
    public void given_financial_record_when_creating_should_format_predicted_value_to_two_decimal_digits() {
        String title = "Uber";
        Category category = Category.newCategory("Clothing");
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        var value = BigDecimal.valueOf(-40.99);

        var expense = FinancialRecord.newFinancialRecord(
                title,
                null,
                Set.of(category),
                value,
                null,
                dueDate,
                FinancialRecordStatus.PENDING,
                FinancialRecordType.EXPENSE
        );

        DomainException exception = assertThrows(DomainException.class, () -> expense.validate(new ThrowsValidationHandler()));
        assertEquals("Value should not be lower than zero", exception.getErrors().getFirst().message());

    }

    @Test
    public void given_null_due_date_when_validating_should_throw_exception() {
        String title = "Uber";
        Category category = Category.newCategory("Clothing");
        var value = BigDecimal.valueOf(40.99);

        var expense = FinancialRecord.newFinancialRecord(
                title,
                null,
                Set.of(category),
                value,
                null,
                null,
                FinancialRecordStatus.PENDING,
                FinancialRecordType.EXPENSE
        );

        DomainException exception = assertThrows(DomainException.class, () -> expense.validate(new ThrowsValidationHandler()));
        assertEquals("Due date should not be null", exception.getErrors().getFirst().message());
    }

    @Test
    public void given_financial_record_should_be_able_to_finish_itself() {
        var value = BigDecimal.valueOf(35.90);
        String title = "Uber";
        String longDescription = "Shopping";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Category category = Category.newCategory("Clothing");

        var expense = FinancialRecord.newFinancialRecord(
                title,
                longDescription,
                Set.of(category),
                value,
                null,
                dueDate,
                FinancialRecordStatus.PENDING,
                FinancialRecordType.EXPENSE
        );

        assertEquals(BigDecimal.valueOf(35.90).setScale(2, RoundingMode.HALF_UP), expense.getPredictedValue());
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), expense.getActualValue());
        assertEquals(FinancialRecordStatus.PENDING, expense.getStatus());

        assertDoesNotThrow(() -> expense.finishFinancialRecord(BigDecimal.valueOf(40.00)));

        assertEquals(BigDecimal.valueOf(35.90).setScale(2, RoundingMode.HALF_UP), expense.getPredictedValue());
        assertEquals(BigDecimal.valueOf(40.00).setScale(2, RoundingMode.HALF_UP), expense.getActualValue());
        assertEquals(FinancialRecordStatus.OK, expense.getStatus());
    }

    @Test
    public void given_financial_record_should_be_able_to_add_category() {
        var value = BigDecimal.valueOf(35.90);
        String title = "Uber";
        var longDescription = "Hospital ride";
        LocalDate dueDate = LocalDate.of(2024, 2, 1);
        Category category = Category.newCategory("Uber");

        var expense = FinancialRecord.newFinancialRecord(
                title,
                longDescription,
                Set.of(category),
                value,
                null,
                dueDate,
                FinancialRecordStatus.PENDING,
                FinancialRecordType.EXPENSE
        );

        assertEquals(BigDecimal.valueOf(35.90).setScale(2, RoundingMode.HALF_UP), expense.getPredictedValue());
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), expense.getActualValue());
        assertEquals(FinancialRecordStatus.PENDING, expense.getStatus());

        assertDoesNotThrow(() -> expense.addCategory(Category.newCategory("Travel")));

        assertEquals(2, expense.getCategories().size());
        assertTrue(() -> expense.getCategories().stream().anyMatch(category1 -> category1.getName().equals("Uber")));
        assertTrue(() -> expense.getCategories().stream().anyMatch(category2 -> category2.getName().equals("Travel")));

    }


    @Test
    public void given_financial_record_should_be_able_to_remove_category_by_id() {
        var value = BigDecimal.valueOf(35.90);
        var title = "Uber";
        var longDescription = "Groceries ride";

        var dueDate = LocalDate.of(2024, 2, 1);
        var uberCategory = Category.newCategory("Uber");
        var travelCategory = Category.newCategory("Travel");

        var expense = FinancialRecord.newFinancialRecord(
                title,
                longDescription,
                Set.of(uberCategory, travelCategory),
                value,
                null,
                dueDate,
                FinancialRecordStatus.PENDING,
                FinancialRecordType.EXPENSE
        );

        assertEquals(BigDecimal.valueOf(35.90).setScale(2, RoundingMode.HALF_UP), expense.getPredictedValue());
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), expense.getActualValue());
        assertEquals(FinancialRecordStatus.PENDING, expense.getStatus());
        assertEquals(2, expense.getCategories().size());
        assertTrue(() -> expense.getCategories().stream().anyMatch(category1 -> category1.getName().equals("Uber")));
        assertTrue(() -> expense.getCategories().stream().anyMatch(category2 -> category2.getName().equals("Travel")));

        assertDoesNotThrow(() -> expense.removeCategoryById(uberCategory.getId()));

        assertEquals(1, expense.getCategories().size());
        assertTrue(() -> expense.getCategories().stream().noneMatch(category1 -> category1.getName().equals("Uber")));
        assertTrue(() -> expense.getCategories().stream().anyMatch(category2 -> category2.getName().equals("Travel")));

    }
}
