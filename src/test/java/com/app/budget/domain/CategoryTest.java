package com.app.budget.domain;

import com.app.budget.exceptions.CategoryException;
import org.junit.Test;

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
}
