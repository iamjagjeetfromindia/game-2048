package game.two048.action;

import game.two048.Board;
import game.two048.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MergeRowLeftTest {

    @Test
    void testMergeLeftSimple() {
        Board board = new Board(1, 4);
        // Set up row: [2, 2, 4, null]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(2);
        board.getCells().get(2).setValue(4);
        board.getCells().get(3).setValue(null);

        MergeRowLeft mergeLeft = new MergeRowLeft();
        mergeLeft.execute(board);

        Cell[] row = board.getRow(0);
        assertEquals(4, row[0].getValue()); // 2+2 merged
        assertEquals(4, row[1].getValue()); // 4 shifted left
        assertNull(row[2].getValue());
        assertNull(row[3].getValue());
    }

    @Test
    void testMergeLeftNoMerge() {
        Board board = new Board(1, 4);
        // Set up row: [2, 4, 8, null]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(4);
        board.getCells().get(2).setValue(8);
        board.getCells().get(3).setValue(null);

        MergeRowLeft mergeLeft = new MergeRowLeft();
        mergeLeft.execute(board);

        Cell[] row = board.getRow(0);
        assertEquals(2, row[0].getValue());
        assertEquals(4, row[1].getValue());
        assertEquals(8, row[2].getValue());
        assertNull(row[3].getValue());
    }

    @Test
    void testMergeLeftDoubleMerge() {
        Board board = new Board(1, 4);
        // Set up row: [2, 2, 2, 2]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(2);
        board.getCells().get(2).setValue(2);
        board.getCells().get(3).setValue(2);

        MergeRowLeft mergeLeft = new MergeRowLeft();
        mergeLeft.execute(board);

        Cell[] row = board.getRow(0);
        assertEquals(4, row[0].getValue()); // 2+2
        assertEquals(4, row[1].getValue()); // 2+2
        assertNull(row[2].getValue());
        assertNull(row[3].getValue());
    }

    // Test cases for edge cases and various scenarios courtesy co-pilot
    @Test
    void testMergeLeftAllNulls() {
        Board board = new Board(1, 4);
        // Set up row: [null, null, null, null]
        for (int i = 0; i < 4; i++) {
            board.getCells().get(i).setValue(null);
        }
        MergeRowLeft mergeLeft = new MergeRowLeft();
        mergeLeft.execute(board);

        Cell[] row = board.getRow(0);
        for (Cell cell : row) {
            assertNull(cell.getValue());
        }
    }

    @Test
    void testMergeLeftSingleValue() {
        Board board = new Board(1, 4);
        // Set up row: [null, null, 8, null]
        board.getCells().get(0).setValue(null);
        board.getCells().get(1).setValue(null);
        board.getCells().get(2).setValue(8);
        board.getCells().get(3).setValue(null);
        MergeRowLeft mergeLeft = new MergeRowLeft();
        mergeLeft.execute(board);

        Cell[] row = board.getRow(0);
        assertEquals(8, row[0].getValue());
        assertNull(row[1].getValue());
        assertNull(row[2].getValue());
        assertNull(row[3].getValue());
    }

    @Test
    void testMergeLeftAlternatingPairs() {
        Board board = new Board(1, 6);
        // Set up row: [2, 2, 4, 4, 8, 8]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(2);
        board.getCells().get(2).setValue(4);
        board.getCells().get(3).setValue(4);
        board.getCells().get(4).setValue(8);
        board.getCells().get(5).setValue(8);

        MergeRowLeft mergeLeft = new MergeRowLeft();
        mergeLeft.execute(board);

        Cell[] row = board.getRow(0);
        assertEquals(4, row[0].getValue());
        assertEquals(8, row[1].getValue());
        assertEquals(16, row[2].getValue());
        assertNull(row[3].getValue());
        assertNull(row[4].getValue());
        assertNull(row[5].getValue());
    }

    @Test
    void testMergeLeftLargeValues() {
        Board board = new Board(1, 4);
        // Set up row: [1024, 1024, null, null]
        board.getCells().get(0).setValue(1024);
        board.getCells().get(1).setValue(1024);
        board.getCells().get(2).setValue(null);
        board.getCells().get(3).setValue(null);

        MergeRowLeft mergeLeft = new MergeRowLeft();
        mergeLeft.execute(board);

        Cell[] row = board.getRow(0);
        assertEquals(2048, row[0].getValue());
        assertNull(row[1].getValue());
        assertNull(row[2].getValue());
        assertNull(row[3].getValue());
    }

    @Test
    void testMergeLeftNoMergeWithGaps() {
        Board board = new Board(1, 4);
        // Set up row: [2, null, 2, null]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(null);
        board.getCells().get(2).setValue(2);
        board.getCells().get(3).setValue(null);

        MergeRowLeft mergeLeft = new MergeRowLeft();
        mergeLeft.execute(board);

        Cell[] row = board.getRow(0);
        assertEquals(4, row[0].getValue());
        assertNull(row[1].getValue());
        assertNull(row[2].getValue());
        assertNull(row[3].getValue());
    }
}
