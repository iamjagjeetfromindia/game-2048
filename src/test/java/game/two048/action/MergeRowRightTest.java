package game.two048.action;

import game.two048.model.Board;
import game.two048.model.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MergeRowRightTest {

    @Test
    void testMergeRightSimple() {
        Board board = new Board(1, 4);
        // Set up row: [2, 2, 4, null]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(2);
        board.getCells().get(2).setValue(4);
        board.getCells().get(3).setValue(null);

        MergeRowRight mergeRight = new MergeRowRight();
        mergeRight.execute(board);

        Cell[] row = board.getRow(0);
        assertNull(row[0].getValue());
        assertNull(row[1].getValue());
        assertEquals(4, row[2].getValue()); // 2+2 merged, shifted right
        assertEquals(4, row[3].getValue()); // 4 shifted right
    }

    @Test
    void testMergeRightNoMerge() {
        Board board = new Board(1, 4);
        // Set up row: [null, 2, 4, 8]
        board.getCells().get(0).setValue(null);
        board.getCells().get(1).setValue(2);
        board.getCells().get(2).setValue(4);
        board.getCells().get(3).setValue(8);

        MergeRowRight mergeRight = new MergeRowRight();
        mergeRight.execute(board);

        Cell[] row = board.getRow(0);
        assertNull(row[0].getValue());
        assertEquals(2, row[1].getValue());
        assertEquals(4, row[2].getValue());
        assertEquals(8, row[3].getValue());
    }

    @Test
    void testMergeRightDoubleMerge() {
        Board board = new Board(1, 4);
        // Set up row: [2, 2, 2, 2]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(2);
        board.getCells().get(2).setValue(2);
        board.getCells().get(3).setValue(2);

        MergeRowRight mergeRight = new MergeRowRight();
        mergeRight.execute(board);

        Cell[] row = board.getRow(0);
        assertNull(row[0].getValue());
        assertNull(row[1].getValue());
        assertEquals(4, row[2].getValue());
        assertEquals(4, row[3].getValue());
    }

    @Test
    void testMergeRightAllNulls() {
        Board board = new Board(1, 4);
        for (int i = 0; i < 4; i++) {
            board.getCells().get(i).setValue(null);
        }
        MergeRowRight mergeRight = new MergeRowRight();
        mergeRight.execute(board);

        Cell[] row = board.getRow(0);
        for (Cell cell : row) {
            assertNull(cell.getValue());
        }
    }

    @Test
    void testMergeRightSingleValue() {
        Board board = new Board(1, 4);
        board.getCells().get(0).setValue(null);
        board.getCells().get(1).setValue(null);
        board.getCells().get(2).setValue(8);
        board.getCells().get(3).setValue(null);

        MergeRowRight mergeRight = new MergeRowRight();
        mergeRight.execute(board);

        Cell[] row = board.getRow(0);
        assertNull(row[0].getValue());
        assertNull(row[1].getValue());
        assertNull(row[2].getValue());
        assertEquals(8, row[3].getValue());
    }

    @Test
    void testMergeRightAlternatingPairs() {
        Board board = new Board(1, 6);
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(2);
        board.getCells().get(2).setValue(4);
        board.getCells().get(3).setValue(4);
        board.getCells().get(4).setValue(8);
        board.getCells().get(5).setValue(8);

        MergeRowRight mergeRight = new MergeRowRight();
        mergeRight.execute(board);

        Cell[] row = board.getRow(0);
        assertNull(row[0].getValue());
        assertNull(row[1].getValue());
        assertNull(row[2].getValue());
        assertEquals(4, row[3].getValue());
        assertEquals(8, row[4].getValue());
        assertEquals(16, row[5].getValue());
    }

    @Test
    void testMergeRightLargeValues() {
        Board board = new Board(1, 4);
        board.getCells().get(0).setValue(1024);
        board.getCells().get(1).setValue(1024);
        board.getCells().get(2).setValue(null);
        board.getCells().get(3).setValue(null);

        MergeRowRight mergeRight = new MergeRowRight();
        mergeRight.execute(board);

        Cell[] row = board.getRow(0);
        assertNull(row[0].getValue());
        assertNull(row[1].getValue());
        assertNull(row[2].getValue());
        assertEquals(2048, row[3].getValue());
    }

    @Test
    void testMergeRightNoMergeWithGaps() {
        Board board = new Board(1, 4);
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(null);
        board.getCells().get(2).setValue(2);
        board.getCells().get(3).setValue(null);

        MergeRowRight mergeRight = new MergeRowRight();
        mergeRight.execute(board);

        Cell[] row = board.getRow(0);
        assertNull(row[0].getValue());
        assertNull(row[1].getValue());
        assertNull(row[2].getValue());
        assertEquals(4, row[3].getValue());
    }
}
