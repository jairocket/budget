package com.app.budget.infrastructure.mappers;

import com.app.budget.core.domain.Board;
import com.app.budget.core.domain.Category;
import com.app.budget.core.domain.Expense;
import com.app.budget.core.domain.Income;
import com.app.budget.core.enums.FinancialRecordStatus;
import com.app.budget.core.enums.FinancialRecordType;
import com.app.budget.infrastructure.persistence.entities.BoardEntity;
import com.app.budget.infrastructure.persistence.entities.FinancialRecordEntity;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardMapperTest {
    private final BoardMapper boardMapper = new BoardMapper();

    private static @NotNull Board getBoard() {
        Expense expense = new Expense(
                1L,
                Set.of(new Category(3L, "health")),
                "consulta",
                "oftalmologista",
                500.00,
                499.90,
                LocalDate.of(2024, 10, 2),
                FinancialRecordStatus.OK
        );
        Income income = new Income(
                2L,
                Set.of(new Category(1L, "health")),
                "consulta",
                "oftalmologista",
                2500.00,
                2499.90,
                LocalDate.of(2024, 10, 2),
                FinancialRecordStatus.OK
        );

        return new Board(4L, Set.of(income), 5L, Set.of(expense));
    }

    @Test
    public void shouldBeAbleToConvertToDomain() {
        FinancialRecordEntity expense = new FinancialRecordEntity(
                500.00 * -1,
                Set.of(new Category(1L, "health")),
                "consulta",
                LocalDate.of(2024, 10, 2),
                2L,
                499.90 * -1,
                FinancialRecordStatus.OK,
                "oftalmologista",
                FinancialRecordType.EXPENSE
        );
        FinancialRecordEntity income = new FinancialRecordEntity(
                2500.00,
                Set.of(new Category(1L, "salary")),
                "salário de 11/2024",
                LocalDate.of(2024, 10, 1),
                3L,
                2500.90,
                FinancialRecordStatus.OK,
                "oftalmologista",
                FinancialRecordType.INCOME
        );

        BoardEntity boardEntity = new BoardEntity(4L, Set.of(expense, income), 5L);
        Board board = boardMapper.toDomain(boardEntity);

        assertEquals(4L, board.getId(), 0);
        assertEquals(5L, board.getUserId(), 0);
        assertEquals(2500.00, board.getTotalActualIncomes(), 0);
        assertEquals(2500.90, board.getTotalPredictedIncomes(), 0);
        assertEquals(499.90, board.getTotalPredictedExpenses(), 0);
        assertEquals(500.00, board.getTotalActualExpenses(), 0);

    }

    @Test
    public void shouldBeAbleToConvertToEntity() {
        Board board = getBoard();
        BoardEntity boardEntity = boardMapper.toEntity(board);

        assertEquals(5L, boardEntity.getId(), 0);
        assertEquals(2, boardEntity.getFinancialRecordsList().size());
        assertTrue(boardEntity
                .getFinancialRecordsList()
                .stream()
                .anyMatch(
                        financialRecordEntity ->
                                financialRecordEntity.getId().equals(2L) &&
                                        financialRecordEntity.getType().equals(FinancialRecordType.INCOME)));
        assertTrue(boardEntity
                .getFinancialRecordsList()
                .stream()
                .anyMatch(
                        financialRecordEntity ->
                                financialRecordEntity.getId().equals(1L) &&
                                        financialRecordEntity.getType().equals(FinancialRecordType.EXPENSE)));

    }
}
