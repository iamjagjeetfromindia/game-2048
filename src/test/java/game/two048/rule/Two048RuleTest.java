
package game.two048.rule;

import game.two048.Board;
import org.junit.jupiter.api.Test;

import static game.two048.util.Constants.WINNING_VALUE;
import static org.junit.jupiter.api.Assertions.*;

class Two048RuleTest {

    @Test
    void testEvaluateWin() {
        Board board = new Board(2, 2);
        board.getCells().get(0).setValue(WINNING_VALUE);
        Two048Rule rule = new Two048Rule();
        assertTrue(rule.evaluate(board));
    }

    @Test
    void testEvaluateNoWin() {
        Board board = new Board(2, 2);
        board.getCells().forEach(cell -> cell.setValue(2));
        Two048Rule rule = new Two048Rule();
        assertFalse(rule.evaluate(board));
    }

    @Test
    void testEvaluateWithNulls() {
        Board board = new Board(2, 2);
        board.getCells().get(0).setValue(null);
        board.getCells().get(1).setValue(1024);
        board.getCells().get(2).setValue(512);
        board.getCells().get(3).setValue(128);
        Two048Rule rule = new Two048Rule();
        assertFalse(rule.evaluate(board));
    }
}