package game.two048.action;

import game.two048.Board;
import game.two048.Cell;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MergeColumnDownTest {

    @Test
    void testMergeDownSimple() {
        Board board = new Board(4, 1);
        // Set up column: [2], [2], [4], [null]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(2);
        board.getCells().get(2).setValue(4);
        board.getCells().get(3).setValue(null);

        MergeColumnDown mergeDown = new MergeColumnDown();
        mergeDown.execute(board);

        Cell[] col = new Cell[4];
        for (int i = 0; i < 4; i++) {
            col[i] = board.getRow(i)[0];
        }
        assertNull(col[0].getValue());
        assertNull(col[1].getValue());
        assertEquals(4, col[2].getValue());
        assertEquals(4, col[3].getValue());
    }

    @Test
    void testMergeDownNoMerge() {
        Board board = new Board(4, 1);
        // Set up column: [2], [4], [8], [null]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(4);
        board.getCells().get(2).setValue(8);
        board.getCells().get(3).setValue(null);

        MergeColumnDown mergeDown = new MergeColumnDown();
        mergeDown.execute(board);

        Cell[] col = new Cell[4];
        for (int i = 0; i < 4; i++) {
            col[i] = board.getRow(i)[0];
        }
        assertNull(col[0].getValue());
        assertEquals(2, col[1].getValue());
        assertEquals(4, col[2].getValue());
        assertEquals(8, col[3].getValue());
    }

    @Test
    void testMergeDownDoubleMerge() {
        Board board = new Board(4, 1);
        // Set up column: [2], [2], [2], [2]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(2);
        board.getCells().get(2).setValue(2);
        board.getCells().get(3).setValue(2);

        MergeColumnDown mergeDown = new MergeColumnDown();
        mergeDown.execute(board);

        Cell[] col = new Cell[4];
        for (int i = 0; i < 4; i++) {
            col[i] = board.getRow(i)[0];
        }
        assertNull(col[0].getValue());
        assertNull(col[1].getValue());
        assertEquals(4, col[2].getValue());
        assertEquals(4, col[3].getValue());
    }

    @Test
    void testMergeDownAllNulls() {
        Board board = new Board(4, 1);
        // Set up column: [null, null, null, null]
        for (int i = 0; i < 4; i++) {
            board.getCells().get(i).setValue(null);
        }
        MergeColumnDown mergeDown = new MergeColumnDown();
        mergeDown.execute(board);

        Cell[] col = new Cell[4];
        for (int i = 0; i < 4; i++) {
            col[i] = board.getRow(i)[0];
            assertNull(col[i].getValue());
        }
    }

    @Test
    void testMergeDownSingleValue() {
        Board board = new Board(4, 1);
        // Set up column: [null], [null], [8], [null]
        board.getCells().get(0).setValue(null);
        board.getCells().get(1).setValue(null);
        board.getCells().get(2).setValue(8);
        board.getCells().get(3).setValue(null);

        MergeColumnDown mergeDown = new MergeColumnDown();
        mergeDown.execute(board);

        Cell[] col = new Cell[4];
        for (int i = 0; i < 4; i++) {
            col[i] = board.getRow(i)[0];
        }
        assertNull(col[0].getValue());
        assertNull(col[1].getValue());
        assertNull(col[2].getValue());
        assertEquals(8, col[3].getValue());
    }

    @Test
    void testMergeDownLargeValues() {
        Board board = new Board(4, 1);
        // Set up column: [1024], [1024], [null], [null]
        board.getCells().get(0).setValue(1024);
        board.getCells().get(1).setValue(1024);
        board.getCells().get(2).setValue(null);
        board.getCells().get(3).setValue(null);

        MergeColumnDown mergeDown = new MergeColumnDown();
        mergeDown.execute(board);

        Cell[] col = new Cell[4];
        for (int i = 0; i < 4; i++) {
            col[i] = board.getRow(i)[0];
        }
        assertNull(col[0].getValue());
        assertNull(col[1].getValue());
        assertNull(col[2].getValue());
        assertEquals(2048, col[3].getValue());
    }

    @Test
    void testMergeDownNoMergeWithGaps() {
        Board board = new Board(4, 1);
        // Set up column: [2], [null], [2], [null]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(null);
        board.getCells().get(2).setValue(2);
        board.getCells().get(3).setValue(null);

        MergeColumnDown mergeDown = new MergeColumnDown();
        mergeDown.execute(board);

        Cell[] col = new Cell[4];
        for (int i = 0; i < 4; i++) {
            col[i] = board.getRow(i)[0];
        }
        assertNull(col[0].getValue());
        assertNull(col[1].getValue());
        assertNull(col[2].getValue());
        assertEquals(4, col[3].getValue());
    }
}