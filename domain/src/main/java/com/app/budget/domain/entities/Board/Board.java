package com.app.budget.domain.entities.Board;

import com.app.budget.domain.AggregateRoot;
import com.app.budget.domain.entities.FinancialRecord.FinancialRecord;
import com.app.budget.domain.entities.FinancialRecord.enums.FinancialRecordType;
import com.app.budget.domain.entities.User.UserID;
import com.app.budget.domain.validation.ValidationHandler;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Board extends AggregateRoot<BoardID> {
    private final Set<FinancialRecord> financialRecords;
    private final UserID userID;

    private Board(final BoardID boardID, final Set<FinancialRecord> financialRecords, final UserID userID) {
        super(boardID);
        this.financialRecords = financialRecords;
        this.userID = userID;
    }

    public static Board newBoard(final Set<FinancialRecord> financialRecords, final UserID userID) {
        BoardID boardID = BoardID.unique();
        Set<FinancialRecord> records = Optional.ofNullable(financialRecords).orElse(Set.of());

        return new Board(boardID, records, userID);
    }

    public Set<FinancialRecord> getFinancialRecords() {
        return financialRecords;
    }

    public UserID getUserID() {
        return userID;
    }

    public Set<FinancialRecord> getExpenses() {
        if (getFinancialRecords().isEmpty()) {
            return Set.of();
        }
        return getFinancialRecords()
                .stream()
                .filter(record -> record.getType() == FinancialRecordType.EXPENSE)
                .collect(Collectors.toSet());
    }

    public Set<FinancialRecord> getIncomes() {
        if (getFinancialRecords().isEmpty()) {
            return Set.of();
        }
        return getFinancialRecords()
                .stream()
                .filter(record -> record.getType() == FinancialRecordType.INCOME)
                .collect(Collectors.toSet());
    }

    public Double getTotalPredictedIncomes() {
        return getIncomes().stream().reduce(
                0.00,
                (subtotal, income) -> subtotal + income.getPredictedValue(),
                Double::sum
        );
    }

    public Double getTotalPredictedExpenses() {
        return getExpenses().stream().reduce(
                0.00,
                (subtotal, expense) -> subtotal + expense.getPredictedValue(),
                Double::sum
        );
    }

    public Double getTotalActualIncomes() {
        return getIncomes().stream().reduce(
                0.00,
                (subtotal, income) -> subtotal + income.getActualValue(),
                Double::sum
        );
    }

    public Double getTotalActualExpenses() {
        return getExpenses().stream().reduce(
                0.00,
                (subtotal, expense) -> subtotal + expense.getActualValue(),
                Double::sum
        );
    }

    @Override
    public void validate(ValidationHandler handler) {
        new BoardValidator(handler, this).validate();
    }
}
