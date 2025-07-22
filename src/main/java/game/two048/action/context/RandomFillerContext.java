package game.two048.action.context;

import game.two048.Board;
import lombok.Data;

@Data
public class RandomFillerContext {
    private Board board;
    private FillType fillType;
    private boolean fillAllCells;

    public enum FillType {
        TWO_AND_NULL,
        FOUR_AND_NULL,
        TWO_AND_FOUR
    }

    public RandomFillerContext(Board board, FillType fillType,  boolean fillAllCells) {
        this.board = board;
        this.fillType = fillType;
        this.fillAllCells = fillAllCells;
    }
}
