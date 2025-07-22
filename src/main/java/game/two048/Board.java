package game.two048;

import lombok.Data;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Data
public class Board {
    private int rows;
    private int cols;
    private List<Cell> cells;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        initialize(rows);
    }

    public Cell[] getRow(int row) {
        return cells.stream()
                .filter(cell -> cell.getRowIndex() == row)
                .sorted(Comparator.comparing(Cell::getColumnIndex))
                .toArray(Cell[]::new);

    }

    public void replaceRow(int row, Cell[] newRow) {
        // remove existing row
        cells.removeIf(c -> c.getRowIndex() == row);
        cells.addAll(Arrays.asList(newRow));
    }

    public Cell[] getColumn(int col) {
        return cells.stream()
                .filter(cell -> cell.getColumnIndex() == col)
                .sorted(Comparator.comparing(Cell::getRowIndex))
                .toArray(Cell[]::new);
    }

    public void replaceColumn(int col, Cell[] newCol) {
        // remove existing column
        cells.removeIf(c -> c.getColumnIndex() == col);
        cells.addAll(Arrays.asList(newCol));
    }

    public void initialize(int size) {
        cells = new java.util.ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells.add(new Cell(i, j, null));
            }
        }
    }


}