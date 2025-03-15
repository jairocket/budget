package com.app.budget.infrastructure.persistence.entities;

import com.app.budget.core.domain.Category;
import com.app.budget.core.enums.FinancialRecordStatus;
import com.app.budget.core.enums.FinancialRecordType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
public class FinancialRecordEntity {
    private Long id;
    private Set<Category> categories;
    private String title;
    private String description;
    private Double predictedValue;
    private Double actualValue;
    private LocalDate dueDate;
    private FinancialRecordStatus status;
    private FinancialRecordType type;

    public FinancialRecordEntity(
            Double actualValue,
            Set<Category> categories,
            String description,
            LocalDate dueDate,
            Long id,
            Double predictedValue,
            FinancialRecordStatus status,
            String title,
            FinancialRecordType type
    ) {
        this.actualValue = actualValue;
        this.categories = categories;
        this.description = description;
        this.dueDate = dueDate;
        this.id = id;
        this.predictedValue = predictedValue;
        this.status = status;
        this.title = title;
        this.type = type;
    }

    public FinancialRecordEntity(Double actualValue, Set<Category> categories, String description, LocalDate dueDate, Double predictedValue, FinancialRecordStatus status, String title, FinancialRecordType type) {
        this.actualValue = actualValue;
        this.categories = categories;
        this.description = description;
        this.dueDate = dueDate;
        this.predictedValue = predictedValue;
        this.status = status;
        this.title = title;
        this.type = type;
    }

    public FinancialRecordEntity() {
    }

    public Double getActualValue() {
        return actualValue;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Long getId() {
        return id;
    }

    public Double getPredictedValue() {
        return predictedValue;
    }

    public FinancialRecordStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public FinancialRecordType getType() {
        return type;
    }

}
