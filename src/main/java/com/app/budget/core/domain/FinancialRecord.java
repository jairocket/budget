package com.app.budget.core.domain;

import com.app.budget.core.exceptions.FinancialRecordException;
import org.apache.commons.math3.util.Precision;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

abstract class FinancialRecord {
    private Long id;
    private Set<Category> categories;
    private String title;
    private String description;
    private Double predictedValue;
    private Double actualValue;
    private LocalDate dueDate;

    public FinancialRecord(Long id, Set<Category> categories, String title, String description, Double predictedValue, Double actualValue, LocalDate dueDate) {
        validateCategories(categories);
        validateTitle(title);
        validateValue(predictedValue);
        validateValue(actualValue);
        validateDueDate(dueDate);
        description = parsedDescription(description);
        validateDescription(description);
        predictedValue = Optional.ofNullable(predictedValue).orElse(0.00);
        actualValue = Optional.ofNullable(actualValue).orElse(0.00);

        this.id = id;
        this.categories = categories;
        this.title = title;
        this.description = description;
        this.predictedValue = round(predictedValue);
        this.actualValue = round(actualValue);
        this.dueDate = dueDate;
    }

    public FinancialRecord(Set<Category> categories, String title, String description, Double predictedValue, Double actualValue, LocalDate dueDate) {
        validateCategories(categories);
        validateTitle(title);
        validateValue(predictedValue);
        validateValue(actualValue);
        validateDueDate(dueDate);
        description = parsedDescription(description);
        validateDescription(description);
        predictedValue = Optional.ofNullable(predictedValue).orElse(0.00);
        actualValue = Optional.ofNullable(actualValue).orElse(0.00);

        this.categories = categories;
        this.title = title;
        this.predictedValue = round(predictedValue);
        this.actualValue = round(actualValue);
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public Double getPredictedValue() {
        return predictedValue;
    }

    public void setPredictedValue(Double predictedValue) {
        validateValue(predictedValue);
        this.predictedValue = predictedValue;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    public Double getActualValue() {
        if (actualValue == null) return null;
        return actualValue;
    }

    public void setActualValue(Double actualValue) {
        validateValue(actualValue);
        this.actualValue = actualValue;
    }

    private void validateCategories(Set<Category> categories) {
        categories = Optional.ofNullable(categories).orElseThrow(() -> new FinancialRecordException("Categories cannot be null"));

        if (categories.isEmpty()) {
            throw new FinancialRecordException("Should inform at least one category");
        }
    }

    private void validateTitle(String title) {
        if (title == null) {
            throw new FinancialRecordException("Title cannot be null");
        }

        if (title.length() < 3) {
            throw new FinancialRecordException("Title should have at least three characters");
        }

        if (title.length() > 45) {
            throw new FinancialRecordException("Title should have less than forty-five characters");
        }
    }

    private void validateValue(Double value) {
        value = Optional.ofNullable(value).orElse(0.00);
        if (value < 0.00) {
            throw new FinancialRecordException("Value should not be lower than zero");
        }
    }

    private Double round(Double value) {
        return Precision.round(value, 2);
    }

    private void validateDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new FinancialRecordException("Due date should not be null");
        }
    }

    private String parsedDescription(String description) {
        return Optional.ofNullable(description).orElse("");
    }

    private void validateDescription(String description) {
        if (description.length() > 256) {
            throw new FinancialRecordException("Description should have less than 256 characters");
        }
    }
}
