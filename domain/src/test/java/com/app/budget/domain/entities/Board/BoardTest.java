package com.app.budget.domain.entities.Board;

import com.app.budget.domain.entities.Category.Category;
import com.app.budget.domain.entities.FinancialRecord.FinancialRecord;
import com.app.budget.domain.entities.FinancialRecord.FinancialRecordID;
import com.app.budget.domain.entities.FinancialRecord.enums.FinancialRecordStatus;
import com.app.budget.domain.entities.FinancialRecord.enums.FinancialRecordType;
import com.app.budget.domain.entities.User.User;
import com.app.budget.domain.entities.User.enums.UserRoleType;
import com.app.budget.domain.exceptions.DomainException;
import com.app.budget.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private final User user = User.newUser("User", "user@user.com", "P@inK1ller", UserRoleType.USER);

    @Test
    public void given_valid_params_should_be_able_to_create_board() {
        Board board = Board.newBoard(null, user.getId());

        assertNotNull(board.getId());
        assertEquals(board.getUserID(), user.getId());
        assertEquals(0, board.getExpenses().size());
        assertEquals(0, board.getIncomes().size());
        assertDoesNotThrow(() -> board.validate(new ThrowsValidationHandler()));
    }

    @Test
    public void given_null_user_when_validate_should_throw_exception() {
        Board board = Board.newBoard(null, null);
        DomainException exception = assertThrows(DomainException.class, () -> board.validate(new ThrowsValidationHandler()));

        assertEquals("User cannot be null", exception.getErrors().getFirst().message());
    }

    @Test
    public void given_null_financial_record_added_to_board_when_validate_should_throw_exception() {
        Board board = Board.newBoard(null, user.getId());

        assertNotNull(board.getId());
        assertEquals(board.getUserID(), user.getId());
        assertEquals(0, board.getExpenses().size());
        assertEquals(0, board.getIncomes().size());
        assertDoesNotThrow(() -> board.validate(new ThrowsValidationHandler()));

        board.addFinancialRecord(null);

        DomainException exception = assertThrows(DomainException.class, () -> board.validate(new ThrowsValidationHandler()));

        assertEquals("Financial Record cannot be null", exception.getErrors().getFirst().message());
    }

    @Test
    public void given_board_should_be_able_to_get_predicted_income_total() {
        Category category_1 = Category.newCategory("Transṕortation");

        Set<Category> categories = Set.of(category_1);
        var income_1 = FinancialRecord.newFinancialRecord("Uber", "Uber", categories, BigDecimal.valueOf(30.00), BigDecimal.valueOf(30.00), LocalDate.of(2024, 3, 1), FinancialRecordStatus.OK, FinancialRecordType.INCOME);
        var income_2 = FinancialRecord.newFinancialRecord("Uber", "Uber", categories, BigDecimal.valueOf(25.00), BigDecimal.valueOf(25.00), LocalDate.of(2024, 3, 2), FinancialRecordStatus.LATE, FinancialRecordType.INCOME);
        var income_3 = FinancialRecord.newFinancialRecord("Uber", "Uber", categories, BigDecimal.valueOf(45.00), BigDecimal.valueOf(45.00), LocalDate.of(2024, 3, 3), FinancialRecordStatus.PENDING, FinancialRecordType.INCOME);

        Board board = Board.newBoard(Set.of(income_1, income_2, income_3), user.getId());

        assertEquals(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP), board.getTotalPredictedIncomes());
    }

    @Test
    public void given_board_should_be_able_to_get_predicted_expense_total() {
        Category category_1 = Category.newCategory("Transṕortation");

        Set<Category> categories = Set.of(category_1);
        var income_1 = FinancialRecord.newFinancialRecord("Uber", "Uber", categories, BigDecimal.valueOf(30.00), BigDecimal.valueOf(30.00), LocalDate.of(2024, 3, 1), FinancialRecordStatus.OK, FinancialRecordType.INCOME);
        var expense_1 = FinancialRecord.newFinancialRecord("Uber", "Uber", categories, BigDecimal.valueOf(30.00), BigDecimal.valueOf(30.00), LocalDate.of(2024, 3, 1), FinancialRecordStatus.OK, FinancialRecordType.EXPENSE);
        var expense_2 = FinancialRecord.newFinancialRecord("Uber", "Uber", categories, BigDecimal.valueOf(25.00), BigDecimal.valueOf(25.00), LocalDate.of(2024, 3, 2), FinancialRecordStatus.LATE, FinancialRecordType.EXPENSE);
        var expense_3 = FinancialRecord.newFinancialRecord("Uber", "Uber", categories, BigDecimal.valueOf(45.00), BigDecimal.valueOf(45.00), LocalDate.of(2024, 3, 3), FinancialRecordStatus.PENDING, FinancialRecordType.EXPENSE);

        Board board = Board.newBoard(Set.of(income_1, expense_1, expense_2, expense_3), user.getId());

        assertEquals(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP), board.getTotalPredictedExpenses());
    }

    @Test
    public void given_board_should_be_able_to_get_actual_income_total() {
        Category category_1 = Category.newCategory("Transṕortation");

        Set<Category> categories = Set.of(category_1);
        var income_1 = FinancialRecord.newFinancialRecord("Uber", "Uber", categories, BigDecimal.valueOf(30.00), BigDecimal.valueOf(25.00), LocalDate.of(2024, 3, 1), FinancialRecordStatus.OK, FinancialRecordType.INCOME);
        var income_2 = FinancialRecord.newFinancialRecord("Uber", "Uber", categories, BigDecimal.valueOf(25.00), BigDecimal.valueOf(25.00), LocalDate.of(2024, 3, 2), FinancialRecordStatus.LATE, FinancialRecordType.INCOME);
        var income_3 = FinancialRecord.newFinancialRecord("Uber", "Uber", categories, BigDecimal.valueOf(45.00), BigDecimal.valueOf(42.00), LocalDate.of(2024, 3, 3), FinancialRecordStatus.PENDING, FinancialRecordType.INCOME);
        var expense_1 = FinancialRecord.newFinancialRecord("Uber", "Uber", categories, BigDecimal.valueOf(30.00), BigDecimal.valueOf(27.00), LocalDate.of(2024, 3, 1), FinancialRecordStatus.OK, FinancialRecordType.EXPENSE);


        Board board = Board.newBoard(Set.of(income_1, income_2, income_3, expense_1), user.getId());

        assertEquals(BigDecimal.valueOf(92.00).setScale(2, RoundingMode.HALF_UP), board.getTotalActualIncomes());
    }

    @Test
    public void given_board_should_be_able_to_get_actual_expense_total() {
        Category category_1 = Category.newCategory("Transṕortation");

        Set<Category> categories = Set.of(category_1);
        var expense_1 = FinancialRecord.newFinancialRecord("Uber", "Uber", categories, BigDecimal.valueOf(30.00), BigDecimal.valueOf(35.00), LocalDate.of(2024, 3, 1), FinancialRecordStatus.OK, FinancialRecordType.EXPENSE);
        var expense_2 = FinancialRecord.newFinancialRecord("Uber", "Uber", categories, BigDecimal.valueOf(25.00), BigDecimal.valueOf(27.50), LocalDate.of(2024, 3, 2), FinancialRecordStatus.LATE, FinancialRecordType.EXPENSE);
        var expense_3 = FinancialRecord.newFinancialRecord("Uber", "Uber", categories, BigDecimal.valueOf(45.00), BigDecimal.valueOf(42.50), LocalDate.of(2024, 3, 3), FinancialRecordStatus.PENDING, FinancialRecordType.EXPENSE);

        Board board = Board.newBoard(Set.of(expense_1, expense_2, expense_3), user.getId());

        assertEquals(BigDecimal.valueOf(105.00), board.getTotalActualExpenses());
    }

    @Test
    public void given_board_should_be_able_to_add_financial_records() {
        var board = Board.newBoard(null, user.getId());

        assertNotNull(board.getId());
        assertEquals(board.getUserID(), user.getId());
        assertEquals(0, board.getExpenses().size());
        assertEquals(0, board.getIncomes().size());
        assertDoesNotThrow(() -> board.validate(new ThrowsValidationHandler()));

        var category = Category.newCategory("Clothing");
        var title = "Uber";
        var predictedValue = BigDecimal.valueOf(35.90);
        var dueDate = LocalDate.of(2024, 2, 1);

        var expense = FinancialRecord.newFinancialRecord(
                title,
                null,
                Set.of(category),
                predictedValue,
                null,
                dueDate,
                null,
                FinancialRecordType.EXPENSE
        );
        assertDoesNotThrow(() -> board.addFinancialRecord(expense));

        assertEquals(BigDecimal.valueOf(35.90).setScale(2, RoundingMode.HALF_UP), board.getTotalPredictedExpenses());
    }

    @Test
    public void given_board_should_be_able_to_remove_financial_record_by_id() {
        var category = Category.newCategory("Clothing");
        var title = "Uber";
        var predictedValue = BigDecimal.valueOf(35.90);
        var dueDate = LocalDate.of(2024, 2, 1);

        var expense = FinancialRecord.newFinancialRecord(
                title,
                null,
                Set.of(category),
                predictedValue,
                null,
                dueDate,
                null,
                FinancialRecordType.EXPENSE
        );

        var board = Board.newBoard(Set.of(expense), user.getId());

        assertNotNull(board.getId());
        assertEquals(board.getUserID(), user.getId());
        assertEquals(1, board.getExpenses().size());
        assertEquals(0, board.getIncomes().size());
        assertDoesNotThrow(() -> board.validate(new ThrowsValidationHandler()));
        assertEquals(BigDecimal.valueOf(35.90).setScale(2, RoundingMode.HALF_UP), board.getTotalPredictedExpenses());

        assertDoesNotThrow(() -> board.removeFinancialRecordByID(expense.getId()));

        assertEquals(0, board.getExpenses().size());
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), board.getTotalPredictedExpenses());
    }

    @Test
    public void given_wrong_id_should_not_remove_financial_record_from_board() {
        var category = Category.newCategory("Clothing");
        var title = "Uber";
        var predictedValue = BigDecimal.valueOf(35.90);
        var dueDate = LocalDate.of(2024, 2, 1);

        var expense = FinancialRecord.newFinancialRecord(
                title,
                null,
                Set.of(category),
                predictedValue,
                null,
                dueDate,
                null,
                FinancialRecordType.EXPENSE
        );

        var board = Board.newBoard(Set.of(expense), user.getId());

        assertNotNull(board.getId());
        assertEquals(board.getUserID(), user.getId());
        assertEquals(1, board.getExpenses().size());
        assertEquals(0, board.getIncomes().size());
        assertDoesNotThrow(() -> board.validate(new ThrowsValidationHandler()));
        assertEquals(BigDecimal.valueOf(35.90).setScale(2, RoundingMode.HALF_UP), board.getTotalPredictedExpenses());

        assertDoesNotThrow(() -> board.removeFinancialRecordByID(FinancialRecordID.unique()));

        assertEquals(1, board.getExpenses().size());
        assertEquals(BigDecimal.valueOf(35.90).setScale(2, RoundingMode.HALF_UP), board.getTotalPredictedExpenses());
    }
}
