package game.two048.model;


import lombok.Data;

@Data
public class Cell {

    private int rowIndex;
    private int columnIndex;
    private Integer value;

    public Cell(int rowIndex, int columnIndex, Integer value) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.value = value;
    }
}
