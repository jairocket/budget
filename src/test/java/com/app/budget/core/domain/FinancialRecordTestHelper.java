package com.app.budget.core.domain;

import java.time.LocalDate;
import java.util.Set;

class FinancialRecordTestHelper extends FinancialRecord {
    public FinancialRecordTestHelper(Long id, Set<Category> categories, String title, String description, Double predictedValue, Double actualValue, LocalDate dueDate) {
        super(id, categories, title, description, predictedValue, actualValue, dueDate);
    }
}
