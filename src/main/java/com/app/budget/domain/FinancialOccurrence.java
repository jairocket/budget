package com.app.budget.domain;

import com.app.budget.exceptions.FinancialOccurrenceException;
import org.apache.commons.math3.util.Precision;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

abstract class FinancialOccurrence {
    private Long id;
    private Set<Category> categories;
    private String title;
    private Double value;
    private LocalDate dueDate;

    public FinancialOccurrence(Long id, Set<Category> categories, String title, Double value, LocalDate dueDate) {
        validateCategories(categories);
        validateTitle(title);
        validateValue(value);
        validateDueDate(dueDate);

        this.id = id;
        this.categories = categories;
        this.title = title;
        this.value = round(value);
        this.dueDate = dueDate;
    }

    public FinancialOccurrence(Set<Category> categories, String title, Double value, LocalDate dueDate) {
        validateCategories(categories);
        validateTitle(title);
        validateValue(value);
        validateDueDate(dueDate);

        this.categories = categories;
        this.title = title;
        this.value = round(value);
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        validateValue(value);
        this.value = value;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        validateDueDate(dueDate);
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        validateCategories(categories);
        this.categories = categories;
    }

    private void validateCategories(Set<Category> categories) {
        categories = Optional.ofNullable(categories).orElseThrow(() -> new FinancialOccurrenceException("Categories cannot be null"));

        if (categories.isEmpty()) {
            Category others = new Category("Others");
            categories.add(others);
        }
    }

    private void validateTitle(String title) {
        if (title == null) {
            throw new FinancialOccurrenceException("Title cannot be null");
        }

        if (title.length() < 3) {
            throw new FinancialOccurrenceException("Title should have at least three characters");
        }
    }

    private void validateValue(Double value) {
        value = Optional.ofNullable(value).orElseThrow(() -> new FinancialOccurrenceException("Value cannot be null"));

        if (value < 0.00) {
            throw new FinancialOccurrenceException("Value should not be lower than zero");
        }
    }

    private Double round(Double value) {
        return Precision.round(value, 2);
    }

    private void validateDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new FinancialOccurrenceException("Due date should not be lower than zero");
        }
    }

}
