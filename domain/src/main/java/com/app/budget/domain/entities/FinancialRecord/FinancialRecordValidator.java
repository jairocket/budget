package com.app.budget.domain.entities.FinancialRecord;

import com.app.budget.domain.validation.Error;
import com.app.budget.domain.validation.ValidationHandler;
import com.app.budget.domain.validation.Validator;

import java.math.BigDecimal;
import java.util.Optional;

public class FinancialRecordValidator extends Validator {
    private final FinancialRecord financialRecord;

    protected FinancialRecordValidator(ValidationHandler handler, FinancialRecord financialRecord) {
        super(handler);
        this.financialRecord = financialRecord;
    }

    @Override
    public void validate() {
        validateType();
        validateTitle();
        validateDescription();
        validateCategories();
        validateValue(this.financialRecord.getPredictedValue());
        validateValue(this.financialRecord.getActualValue());
        validateDueDate();
    }

    private void validateCategories() {
        if (this.financialRecord.getCategories() == null) {
            this.validationHandler().append(new Error("Categories cannot be null"));
        }

        if (this.financialRecord.getCategories().isEmpty()) {
            this.validationHandler().append(new Error("Should inform at least one category"));
        }
    }

    private void validateTitle() {
        if (this.financialRecord.getTitle() == null) {
            this.validationHandler().append(new Error("Title cannot be null"));
        }

        if (this.financialRecord.getTitle().trim().length() < 3) {
            this.validationHandler().append(new Error("Title should have at least three characters"));
        }

        if (this.financialRecord.getTitle().trim().length() > 45) {
            this.validationHandler().append(new Error("Title should have less than forty-five characters"));
        }
    }

    private void validateValue(BigDecimal value) {
        if (value == null) {
            this.validationHandler().append(new Error("Value cannot be null"));
        }
        value = Optional.ofNullable(value).orElse(BigDecimal.ZERO);

        if (value.doubleValue() < 0.00) {
            this.validationHandler().append(new Error("Value should not be lower than zero"));
        }
    }

    private void validateDueDate() {
        if (this.financialRecord.getDueDate() == null) {
            this.validationHandler().append(new Error("Due date should not be null"));
        }
    }

    private void validateDescription() {
        if (this.financialRecord.getDescription().length() > 256) {
            this.validationHandler().append(new Error("Description should have less than 256 characters"));
        }
    }

    private void validateType() {
        if (this.financialRecord.getType() == null) {
            this.validationHandler().append(new Error("Type should be either 'income' or 'expense'"));

        }
    }
}
