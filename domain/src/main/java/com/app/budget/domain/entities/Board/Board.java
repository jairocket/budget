package com.app.budget.domain.entities.Board;

import com.app.budget.domain.AggregateRoot;
import com.app.budget.domain.entities.FinancialRecord.FinancialRecord;
import com.app.budget.domain.entities.FinancialRecord.FinancialRecordID;
import com.app.budget.domain.entities.FinancialRecord.enums.FinancialRecordType;
import com.app.budget.domain.entities.User.UserID;
import com.app.budget.domain.validation.ValidationHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Board extends AggregateRoot<BoardID> {
    private final UserID userID;
    private Set<FinancialRecord> financialRecords;

    private Board(final BoardID boardID, Set<FinancialRecord> financialRecords, final UserID userID) {
        super(boardID);
        this.financialRecords = financialRecords;
        this.userID = userID;
    }

    public static Board newBoard(Set<FinancialRecord> financialRecords, final UserID userID) {
        BoardID boardID = BoardID.unique();
        Set<FinancialRecord> records = new HashSet<>(Optional.ofNullable(financialRecords).orElse(new HashSet<>()));

        return new Board(boardID, records, userID);
    }

    public Set<FinancialRecord> getFinancialRecords() {
        return financialRecords;
    }

    public void addFinancialRecord(FinancialRecord financialRecord) {
        this.financialRecords.add(financialRecord);
    }

    public void removeFinancialRecordByID(FinancialRecordID id) {
        this.financialRecords = financialRecords.stream()
                .filter(financialRecord -> financialRecord.getId() != id)
                .collect(Collectors.toSet());
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

    public BigDecimal getTotalPredictedIncomes() {
        return BigDecimal.valueOf(
                getIncomes().stream().reduce(
                        0.00,
                        (subtotal, income) -> subtotal + income.getPredictedValue().doubleValue(),
                        Double::sum
                )).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalPredictedExpenses() {
        return BigDecimal.valueOf(getExpenses().stream().reduce(
                0.00,
                (subtotal, expense) -> subtotal + expense.getPredictedValue().doubleValue(),
                Double::sum
        )).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalActualIncomes() {
        return BigDecimal.valueOf(getIncomes().stream().reduce(
                0.00,
                (subtotal, income) -> subtotal + income.getActualValue().doubleValue(),
                Double::sum
        )).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalActualExpenses() {
        return BigDecimal.valueOf(getExpenses().stream().reduce(
                0.00,
                (subtotal, expense) -> subtotal + expense.getActualValue().doubleValue(),
                Double::sum
        ));
    }

    @Override
    public void validate(ValidationHandler handler) {
        new BoardValidator(handler, this).validate();
    }
}
