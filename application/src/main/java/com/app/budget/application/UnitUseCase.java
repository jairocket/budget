package com.app.budget.application;

public abstract class UnitUseCase<INPUT> {
    public abstract void execute(INPUT input);
}
