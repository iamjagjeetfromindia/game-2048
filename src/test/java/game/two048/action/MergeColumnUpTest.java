package game.two048.action;

import game.two048.model.Board;
import game.two048.model.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MergeColumnUpTest {

    @Test
    void testMoveUpSimple() {
        Board board = new Board(4, 1);
        // Set up column: [2], [2], [4], [null]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(2);
        board.getCells().get(2).setValue(4);
        board.getCells().get(3).setValue(null);

        MergeColumnUp mergeUp = new MergeColumnUp();
        mergeUp.execute(board);

        Cell[] col = new Cell[4];
        for (int i = 0; i < 4; i++) {
            col[i] = board.getRow(i)[0];
        }
        assertEquals(4, col[0].getValue()); // 2+2 merged
        assertEquals(4, col[1].getValue()); // 4 shifted up
        assertNull(col[2].getValue());
        assertNull(col[3].getValue());
    }


    @Test
    void testMergeUpNoMerge() {
        Board board = new Board(4, 1);
        // Set up column: [2], [4], [8], [null]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(4);
        board.getCells().get(2).setValue(8);
        board.getCells().get(3).setValue(null);

        MergeColumnUp mergeUp = new MergeColumnUp();
        mergeUp.execute(board);

        Cell[] col = new Cell[4];
        for (int i = 0; i < 4; i++) {
            col[i] = board.getRow(i)[0];
        }
        assertEquals(2, col[0].getValue());
        assertEquals(4, col[1].getValue());
        assertEquals(8, col[2].getValue());
        assertNull(col[3].getValue());
    }

    @Test
    void testMergeUpDoubleMerge() {
        Board board = new Board(4, 1);
        // Set up column: [2], [2], [2], [2]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(2);
        board.getCells().get(2).setValue(2);
        board.getCells().get(3).setValue(2);

        MergeColumnUp mergeUp = new MergeColumnUp();
        mergeUp.execute(board);

        Cell[] col = new Cell[4];
        for (int i = 0; i < 4; i++) {
            col[i] = board.getRow(i)[0];
        }
        assertEquals(4, col[0].getValue());
        assertEquals(4, col[1].getValue());
        assertNull(col[2].getValue());
        assertNull(col[3].getValue());
    }

    @Test
    void testMergeUpAllNulls() {
        Board board = new Board(4, 1);
        // Set up column: [null, null, null, null]
        for (int i = 0; i < 4; i++) {
            board.getCells().get(i).setValue(null);
        }
        MergeColumnUp mergeUp = new MergeColumnUp();
        mergeUp.execute(board);

        Cell[] col = new Cell[4];
        for (int i = 0; i < 4; i++) {
            col[i] = board.getRow(i)[0];
            assertNull(col[i].getValue());
        }
    }

    @Test
    void testMergeUpSingleValue() {
        Board board = new Board(4, 1);
        // Set up column: [null], [null], [8], [null]
        board.getCells().get(0).setValue(null);
        board.getCells().get(1).setValue(null);
        board.getCells().get(2).setValue(8);
        board.getCells().get(3).setValue(null);

        MergeColumnUp mergeUp = new MergeColumnUp();
        mergeUp.execute(board);

        Cell[] col = new Cell[4];
        for (int i = 0; i < 4; i++) {
            col[i] = board.getRow(i)[0];
        }
        assertEquals(8, col[0].getValue());
        assertNull(col[1].getValue());
        assertNull(col[2].getValue());
        assertNull(col[3].getValue());
    }

    @Test
    void testMergeUpLargeValues() {
        Board board = new Board(4, 1);
        // Set up column: [1024], [1024], [null], [null]
        board.getCells().get(0).setValue(1024);
        board.getCells().get(1).setValue(1024);
        board.getCells().get(2).setValue(null);
        board.getCells().get(3).setValue(null);

        MergeColumnUp mergeUp = new MergeColumnUp();
        mergeUp.execute(board);

        Cell[] col = new Cell[4];
        for (int i = 0; i < 4; i++) {
            col[i] = board.getRow(i)[0];
        }
        assertEquals(2048, col[0].getValue());
        assertNull(col[1].getValue());
        assertNull(col[2].getValue());
        assertNull(col[3].getValue());
    }

    @Test
    void testMergeUpNoMergeWithGaps() {
        Board board = new Board(4, 1);
        // Set up column: [2], [null], [2], [null]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(null);
        board.getCells().get(2).setValue(2);
        board.getCells().get(3).setValue(null);

        MergeColumnUp mergeUp = new MergeColumnUp();
        mergeUp.execute(board);

        Cell[] col = new Cell[4];
        for (int i = 0; i < 4; i++) {
            col[i] = board.getRow(i)[0];
        }
        assertEquals(4, col[0].getValue());
        assertNull(col[1].getValue());
        assertNull(col[2].getValue());
        assertNull(col[3].getValue());
    }

    @Test
    void testMergeUpAlternatingPairs() {
        Board board = new Board(6, 1);
        // Set up column: [2, 2, 4, 4, 8, 8]
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(2);
        board.getCells().get(2).setValue(4);
        board.getCells().get(3).setValue(4);
        board.getCells().get(4).setValue(8);
        board.getCells().get(5).setValue(8);

        MergeColumnUp mergeUp = new MergeColumnUp();
        mergeUp.execute(board);

        Cell[] col = new Cell[6];
        for (int i = 0; i < 6; i++) {
            col[i] = board.getRow(i)[0];
        }
        assertEquals(4, col[0].getValue());
        assertEquals(8, col[1].getValue());
        assertEquals(16, col[2].getValue());
        assertNull(col[3].getValue());
        assertNull(col[4].getValue());
        assertNull(col[5].getValue());
    }
}
