package com.app.budget.domain;

import com.app.budget.exceptions.CategoryException;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import static org.junit.Assert.*;

public class CategoryTest {
    @Test
    public void shouldBeAbleToCreateCategory() {
        Category category = new Category(1L, "Transportation");

        assertEquals("Transportation", category.getName());
        assertTrue(category.getId() == 1);
    }

    @Test
    public void shouldThrowExceptionIfNameIsNull() {
        CategoryException exception = assertThrows(CategoryException.class, () -> new Category(null));
        assertEquals("Category name cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfNameHasLessThanThreeCharacters() {
        CategoryException exception = assertThrows(CategoryException.class, () -> new Category("fv"));
        assertEquals("User category should have at least three characters", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfNameHasMoreThanFortyFiveCharacters() {
        String longName = RandomStringUtils.randomAlphabetic(46);
        CategoryException exception = assertThrows(CategoryException.class, () -> new Category(longName));
        assertEquals("User category should have less than forty-five characters", exception.getMessage());
    }

    @Test
    public void shouldValidateNameBeforeUpdateIt() {
        Category category = new Category("Transportation");

        CategoryException exception = assertThrows(CategoryException.class, () -> category.setName("Tr"));
        assertEquals("User category should have at least three characters", exception.getMessage());

    }

    @Test
    public void shouldBeAbleToUpdateName() {
        Category category = new Category("Transportation");

        category.setName("Travel");

        assertEquals("Travel", category.getName());

    }
}
