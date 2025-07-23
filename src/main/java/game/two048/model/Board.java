package game.two048.model;

import lombok.Data;

import java.util.ArrayList;
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
        this.cells = new ArrayList<>();
        this.initialize();
    }

    public Cell[] getRow(int row) {
        return this.cells.stream()
                .filter(cell -> cell.getRowIndex() == row)
                .sorted(Comparator.comparing(Cell::getColumnIndex))
                .toArray(Cell[]::new);

    }

    public void replaceRow(int row, Cell[] newRow) {
        // remove existing row
        this.cells.removeIf(c -> c.getRowIndex() == row);
        this.cells.addAll(Arrays.asList(newRow));
    }

    public Cell[] getColumn(int col) {
        return this.cells.stream()
                .filter(cell -> cell.getColumnIndex() == col)
                .sorted(Comparator.comparing(Cell::getRowIndex))
                .toArray(Cell[]::new);
    }

    public void replaceColumn(int col, Cell[] newCol) {
        // remove existing column
        this.cells.removeIf(c -> c.getColumnIndex() == col);
        this.cells.addAll(Arrays.asList(newCol));
    }

    public void initialize() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.cells.add(new Cell(i, j, null));
            }
        }
    }
}