package com.app.budget.domain;

public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID> {
    public AggregateRoot(ID id) {
        super(id);
    }
}
