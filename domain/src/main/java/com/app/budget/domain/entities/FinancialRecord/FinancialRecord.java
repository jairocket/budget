package com.app.budget.domain.entities.FinancialRecord;

import com.app.budget.domain.AggregateRoot;
import com.app.budget.domain.entities.Category.Category;
import com.app.budget.domain.entities.Category.CategoryID;
import com.app.budget.domain.entities.FinancialRecord.enums.FinancialRecordStatus;
import com.app.budget.domain.entities.FinancialRecord.enums.FinancialRecordType;
import com.app.budget.domain.validation.ValidationHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FinancialRecord extends AggregateRoot<FinancialRecordID> {
    private final String title;
    private final String description;
    private final BigDecimal predictedValue;
    private final LocalDate dueDate;
    private final FinancialRecordType type;
    private Set<Category> categories;
    private BigDecimal actualValue;
    private FinancialRecordStatus status;

    private FinancialRecord(
            final FinancialRecordID financialRecordID,
            final String title,
            final String description,
            final Set<Category> categories,
            final BigDecimal predictedValue,
            final BigDecimal actualValue,
            final LocalDate dueDate,
            final FinancialRecordStatus status,
            final FinancialRecordType type
    ) {
        super(financialRecordID);
        this.categories = categories;
        this.title = title;
        this.description = description;
        this.predictedValue = predictedValue;
        this.actualValue = actualValue;
        this.dueDate = dueDate;
        this.status = status;
        this.type = type;
    }

    public static FinancialRecord newFinancialRecord(
            final String title,
            final String description,
            Set<Category> categories,
            final BigDecimal predictedValue,
            final BigDecimal actualValue,
            final LocalDate dueDate,
            final FinancialRecordStatus status,
            final FinancialRecordType type
    ) {
        var id = FinancialRecordID.unique();
        var parsedDescription = Optional.ofNullable(description).orElse("");
        var parsedStatus = Optional.ofNullable(status).orElse(FinancialRecordStatus.PENDING);
        var parsedPredictedValue = normalize(predictedValue);
        var parsedActualValue = normalize(actualValue);
        categories = new HashSet<>(Optional.ofNullable(categories).orElse(new HashSet<>()));

        return new FinancialRecord(
                id,
                title,
                parsedDescription,
                categories,
                parsedPredictedValue,
                parsedActualValue,
                dueDate,
                parsedStatus,
                type
        );
    }

    private static BigDecimal normalize(BigDecimal value) {
        return Optional.ofNullable(value)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);
    }


    public BigDecimal getActualValue() {
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

    public BigDecimal getPredictedValue() {
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

    public void finishFinancialRecord(BigDecimal actualValue) {
        this.actualValue = normalize(actualValue);
        this.status = FinancialRecordStatus.OK;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategoryById(CategoryID id) {
        this.categories = categories.stream()
                .filter(category -> !category.getId().equals(id))
                .collect(Collectors.toSet());
    }

    @Override
    public void validate(ValidationHandler handler) {
        new FinancialRecordValidator(handler, this).validate();
    }
}
