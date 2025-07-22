package game.two048.action;

import game.two048.Board;
import game.two048.Cell;

public abstract class MergeRow extends BaseMoveMerge {

    @Override
    public void execute(Board board) {
        // Iterate through each row
        for (int i = 0; i < board.getRows(); i++) {
            Cell[] row = board.getRow(i);
            Cell[] movedRow = mergeLine(row, row[0].getRowIndex());
            board.replaceRow(i, movedRow);
        }
    }

    @Override
    protected Cell getCell(int lineNo, int index, Integer value) {
        return new Cell(lineNo, index, value);
    }
}
