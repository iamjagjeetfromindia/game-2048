package game.two048.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testInitializeAndGetRowColumn() {
        Board board = new Board(2, 2);
        assertEquals(4, board.getCells().size());

        Cell[] row0 = board.getRow(0);
        assertEquals(2, row0.length);
        assertEquals(0, row0[0].getRowIndex());
        assertEquals(0, row0[0].getColumnIndex());
        assertNull(row0[0].getValue());

        Cell[] col1 = board.getColumn(1);
        assertEquals(2, col1.length);
        assertEquals(1, col1[0].getColumnIndex());
    }

    @Test
    void testReplaceRow() {
        Board board = new Board(2, 2);
        Cell[] newRow = { new Cell(0, 0, 2), new Cell(0, 1, 4) };
        board.replaceRow(0, newRow);

        Cell[] row0 = board.getRow(0);
        assertEquals(2, row0.length);
        assertEquals(2, row0[0].getValue());
        assertEquals(4, row0[1].getValue());
    }

    @Test
    void testReplaceColumn() {
        Board board = new Board(2, 2);
        Cell[] newCol = { new Cell(0, 1, 8), new Cell(1, 1, 16) };
        board.replaceColumn(1, newCol);

        Cell[] col1 = board.getColumn(1);
        assertEquals(8, col1[0].getValue());
        assertEquals(16, col1[1].getValue());
    }
}