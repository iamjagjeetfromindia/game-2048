package game.two048.action;

import game.two048.model.Board;
import game.two048.model.Cell;


public abstract class BaseMoveMerge implements ActionHandler<Board> {

    protected Cell[] mergeLine(Cell[] row, int lineNo) {
        Cell[] slide = this.slideLine(row, lineNo);   //slide the cells so that all non-null values are at the start
        return this.mergeAdjacentCell(slide, lineNo); // merge adjacent cells with the same value and move null values to the end
    }


    protected Cell[] mergeAdjacentCell(Cell[] row, int lineNo) {
        int len = row.length;
        Cell[] merged = new Cell[len];

        int i = this.getInitialPosition(len);
        int insertPos = i;
        final int step = this.getStep();

        while (this.shouldContinueProcessing(i, len)) {
            Integer currVal = row[i].getValue();
            int nextIndex = i + step;

            if (currVal != null && this.shouldContinueProcessing(nextIndex, len)
                    && currVal.equals(row[nextIndex].getValue())) {
                merged[insertPos] =  this.getCell(lineNo, insertPos, currVal * 2);//new Cell(rowNo, insertPos, currVal * 2);
                insertPos += step;
                i += 2 * step;
            } else {
                merged[insertPos] = this.getCell(lineNo, insertPos, currVal);//new Cell(rowNo, insertPos, currVal);
                insertPos += step;
                i += step;
            }
        }

        this.fillEmptyCells(merged, insertPos, lineNo, step);
        return merged;
    }

    protected Cell[] slideLine(Cell[] row, int lineNo) {
        int len = row.length;
        Cell[] result = new Cell[len];

        int readPos = this.getInitialPosition(len);
        int writePos = readPos;
        final int step = this.getStep();

        while (this.shouldContinueProcessing(readPos, len)) {
            if (row[readPos].getValue() != null) {
                result[writePos] = this.getCell(lineNo, writePos, row[readPos].getValue());//new Cell(rowNo, writePos, row[readPos].getValue());
                writePos += step;
            }
            readPos += step;
        }

        this.fillEmptyCells(result, writePos, lineNo, step);
        return result;
    }

    protected abstract int getInitialPosition(int length);
    protected abstract int getStep();
    protected abstract boolean shouldContinueProcessing(int index, int length);
    protected abstract Cell getCell(int lineNo, int index, Integer value);


    private void fillEmptyCells(Cell[] row, int startPos, int lineNo, int step) {
        int pos = startPos;
        while ((step > 0 && pos < row.length) || (step < 0 && pos >= 0)) {
            row[pos] = this.getCell(lineNo, pos, null);//new Cell(rowNo, pos, null);
            pos += step;
        }
    }
}