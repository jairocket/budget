package com.app.budget.infrastructure.mappers;

import com.app.budget.core.domain.Category;
import com.app.budget.infrastructure.persistence.entities.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toDomain(CategoryEntity entity) {
        return new Category(entity.getId(), entity.getName());
    }

    public CategoryEntity toEntity(Category domain) {
        return new CategoryEntity(domain.getId(), domain.getName());
    }
}
