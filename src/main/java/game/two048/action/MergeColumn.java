package game.two048.action;

import game.two048.model.Board;
import game.two048.model.Cell;

public abstract class MergeColumn extends BaseMoveMerge {

    @Override
    public void execute(Board board) {
        // Iterate through each column
        for (int i = 0; i < board.getCols(); i++) {
            Cell[] column = board.getColumn(i);
            Cell[] movedColumn = mergeLine(column, column[0].getColumnIndex());
            board.replaceColumn(i, movedColumn);
        }
    }

    @Override
    protected Cell getCell(int lineNo, int index, Integer value) {
        return new Cell(index, lineNo, value);
    }
}
