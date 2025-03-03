package com.app.budget.infrastructure.persistence.entities;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class BoardEntity {
    private Long id;
    private Long userId;
    private Set<TransactionEntity> transactionsList;

    public BoardEntity(Long id, Set<TransactionEntity> transactionsList, Long userId) {
        this.id = id;
        this.transactionsList = transactionsList;
        this.userId = userId;
    }

    public BoardEntity(Long userId) {
        this.userId = userId;
        this.transactionsList = new HashSet<>();
    }

    public BoardEntity() {
    }

    public BoardEntity(Set<TransactionEntity> transactionsList, Long userId) {
        this.transactionsList = transactionsList;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public Set<TransactionEntity> getTransactionsList() {
        return transactionsList;
    }

    public Long getUserId() {
        return userId;
    }
}
