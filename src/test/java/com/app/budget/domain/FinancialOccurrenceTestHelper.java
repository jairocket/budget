package com.app.budget.domain;

import java.time.LocalDate;
import java.util.Set;

class FinancialOccurrenceTestHelper extends FinancialOccurrence {
    public FinancialOccurrenceTestHelper(Long id, Set<Category> categories, String title, Double value, LocalDate dueDate) {
        super(id, categories, title, value, dueDate);
    }
}
