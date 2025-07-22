
package game.two048.rule;

import game.two048.model.Board;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NoMoreMoveRuleTest {

    @Test
    void testEvaluateNoEmptyCells() {
        Board board = new Board(2, 2);
        board.getCells().forEach(cell -> cell.setValue(2));
        NoMoreMoveRule rule = new NoMoreMoveRule();
        assertTrue(rule.evaluate(board));
    }

    @Test
    void testEvaluateWithEmptyCell() {
        Board board = new Board(2, 2);
        board.getCells().get(0).setValue(2);
        board.getCells().get(1).setValue(null);
        board.getCells().get(2).setValue(4);
        board.getCells().get(3).setValue(8);
        NoMoreMoveRule rule = new NoMoreMoveRule();
        assertFalse(rule.evaluate(board));
    }
}
