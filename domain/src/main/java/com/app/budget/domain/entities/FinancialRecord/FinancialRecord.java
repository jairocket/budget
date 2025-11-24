package com.app.budget.domain.entities.FinancialRecord;

import com.app.budget.domain.AggregateRoot;
import com.app.budget.domain.entities.Category.Category;
import com.app.budget.domain.entities.FinancialRecord.enums.FinancialRecordStatus;
import com.app.budget.domain.entities.FinancialRecord.enums.FinancialRecordType;
import com.app.budget.domain.validation.ValidationHandler;
import org.apache.commons.math3.util.Precision;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public class FinancialRecord extends AggregateRoot<FinancialRecordID> {
    private final Set<Category> categories;
    private final String title;
    private final String description;
    private final Double predictedValue;
    private final Double actualValue;
    private final LocalDate dueDate;
    private final FinancialRecordStatus status;
    private final FinancialRecordType type;

    private FinancialRecord(
            final FinancialRecordID financialRecordID,
            final String title,
            final String description,
            final Set<Category> categories,
            final Double predictedValue,
            final Double actualValue,
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
            final Set<Category> categories,
            final Double predictedValue,
            final Double actualValue,
            final LocalDate dueDate,
            final FinancialRecordStatus status,
            final FinancialRecordType type
    ) {
        var id = FinancialRecordID.unique();
        var parsedDescription = Optional.ofNullable(description).orElse("");
        var parsedStatus = Optional.ofNullable(status).orElse(FinancialRecordStatus.PENDING);
        var parsedPredictedValue = parseValue(predictedValue);
        var parsedActualValue = parseValue(actualValue);

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

    private static Double round(Double value) {
        return Precision.round(value, 2);
    }

    private static Double parseValue(Double value) {
        value = Optional.ofNullable(value).orElse(0.00);
        return round(value);
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

    @Override
    public void validate(ValidationHandler handler) {
        new FinancialRecordValidator(handler, this).validate();
    }
}
