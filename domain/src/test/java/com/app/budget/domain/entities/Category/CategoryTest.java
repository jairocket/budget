package com.app.budget.domain.entities.Category;

import com.app.budget.domain.exceptions.DomainException;
import com.app.budget.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {
    @Test
    public void shouldBeAbleToCreateCategory() {
        Category category = Category.newCategory("Transportation");

        assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));
        assertEquals("Transportation", category.getName());
        assertNotNull(category.getId());
    }

    @Test
    public void shouldThrowExceptionIfNameIsNull() {
        final Category category = Category.newCategory(null);
        DomainException exception = assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));
        assertEquals("Category name cannot be null", exception.getErrors().getFirst().message());
    }

    @Test
    public void shouldThrowExceptionIfNameHasLessThanThreeCharacters() {
        final Category category = Category.newCategory("fv");
        DomainException exception = assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));
        assertEquals("User category should have at least three characters", exception.getErrors().getFirst().message());
    }

    @Test
    public void shouldThrowExceptionIfNameHasMoreThanFortyFiveCharacters() {
        final String longName = RandomStringUtils.randomAlphabetic(46);
        final Category category = Category.newCategory(longName);
        DomainException exception = assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));
        assertEquals("User category should have less than forty-five characters", exception.getErrors().getFirst().message());
    }

    @Test
    public void given_category_should_be_able_to_update_name() {
        Category category = Category.newCategory("Transportation");
        assertEquals("Transportation", category.getName());
        assertNotNull(category.getId());

        assertDoesNotThrow(() -> category.setName("Travel"));

        assertEquals("Travel", category.getName());
    }
}
