package com.app.budget.domain;

import com.app.budget.enums.UserRole;
import com.app.budget.exceptions.BoardException;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void shouldBeAbleToCreateBoard() {
        User user = new User(1L, "User", "user@user.com", "P@inK1ller", UserRole.USER);

        Board board = new Board(2L, user);

        assertTrue(board.getId() == 2);
        assertTrue(board.getUser().getId() == 1);
        assertEquals(0, board.getExpenses().size());
        assertEquals(0, board.getIncomes().size());
    }

    @Test
    public void shouldThrowExceptionWhenUserIsNull() {
        BoardException exception = assertThrows(BoardException.class, () -> new Board(null));

        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTryToUpdatesIncomesToNull() {
        User user = new User(1L, "User", "user@user.com", "P@inK1ller", UserRole.USER);

        Board board = new Board(2L, user);
        BoardException exception = assertThrows(BoardException.class, () -> board.setIncomes(null));

        assertEquals("Incomes cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTryToUpdatesExpensesToNull() {
        User user = new User(1L, "User", "user@user.com", "P@inK1ller", UserRole.USER);

        Board board = new Board(2L, user);
        BoardException exception = assertThrows(BoardException.class, () -> board.setExpenses(null));

        assertEquals("Expenses cannot be null", exception.getMessage());
    }


}
