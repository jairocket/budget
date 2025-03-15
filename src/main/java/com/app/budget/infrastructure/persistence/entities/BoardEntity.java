package com.app.budget.infrastructure.persistence.entities;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class BoardEntity {
    private Long id;
    private Long userId;
    private Set<FinancialRecordEntity> financialRecordsList;

    public BoardEntity(Long id, Set<FinancialRecordEntity> financialRecordsList, Long userId) {
        this.id = id;
        this.financialRecordsList = financialRecordsList;
        this.userId = userId;
    }

    public BoardEntity(Long userId) {
        this.userId = userId;
        this.financialRecordsList = new HashSet<>();
    }

    public BoardEntity() {
    }

    public BoardEntity(Set<FinancialRecordEntity> financialRecordsList, Long userId) {
        this.financialRecordsList = financialRecordsList;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public Set<FinancialRecordEntity> getFinancialRecordsList() {
        return financialRecordsList;
    }

    public Long getUserId() {
        return userId;
    }
}
