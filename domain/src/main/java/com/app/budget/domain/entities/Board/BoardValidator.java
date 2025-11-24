package com.app.budget.domain.entities.Board;

import com.app.budget.domain.validation.Error;
import com.app.budget.domain.validation.ValidationHandler;
import com.app.budget.domain.validation.Validator;

public class BoardValidator extends Validator {
    private final Board board;

    protected BoardValidator(final ValidationHandler handler, final Board board) {
        super(handler);
        this.board = board;
    }

    @Override
    public void validate() {
        if (this.board.getUserID() == null) {
            validationHandler().append(new Error("User cannot be null"));
        }
    }
}
