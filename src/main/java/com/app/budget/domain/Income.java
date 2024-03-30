package com.app.budget.domain;

import java.time.LocalDate;
import java.util.Set;

public class Income extends FinancialOccurrence {
    public Income(Long id, Set<Category> categories, String title, Double value, LocalDate dueDate) {
        super(id, categories, title, value, dueDate);
    }

    public Income(Set<Category> categories, String title, Double value, LocalDate dueDate) {
        super(categories, title, value, dueDate);
    }
}
