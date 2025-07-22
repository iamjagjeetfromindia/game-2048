package game.two048.manager;

import game.two048.model.Board;
import game.two048.model.Cell;
import game.two048.model.GameState;
import game.two048.model.MoveDirection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    @Test
    void testMoveChangesBoard() {
        GameManager manager = new GameManager(2, 2);
        Board before = manager.getBoard();
        Integer[] beforeValues = before.getCells().stream().map(Cell::getValue).toArray(Integer[]::new);

        manager.move(MoveDirection.LEFT);
        Board after = manager.getBoard();
        Integer[] afterValues = after.getCells().stream().map(Cell::getValue).toArray(Integer[]::new);

        // Board should change after move
        assertFalse(java.util.Arrays.equals(beforeValues, afterValues));
    }

    @Test
    void testGameStateInProgress() {
        GameManager manager = new GameManager(2, 2);
        assertEquals(GameState.IN_PROGRESS, manager.getGameState());
    }

    @Test
    void testGameStateWin() {
        GameManager manager = new GameManager(2, 2);
        // Simulate win by setting a cell to 2048
        manager.getBoard().getCells().get(0).setValue(2048);
        assertEquals(GameState.WIN, manager.getGameState());
    }

    @Test
    void testGameStateGameOver() {
        GameManager manager = new GameManager(2, 2);
        // Fill all cells with non-mergeable values
        manager.getBoard().getCells().forEach(cell -> cell.setValue(2));
        assertEquals(GameState.GAME_OVER, manager.getGameState());
    }
}