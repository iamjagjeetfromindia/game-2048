package game.two048.rule;

import game.two048.model.Board;

public class NoMoreMoveRule implements Rule{
    private static final int WINNING_VALUE = 2048;
    @Override
    public int getOrder() {
        return 1;
    }


   @Override
   public boolean evaluate(Board board) {
       return board.getCells().stream().noneMatch(cell -> cell.getValue() == null);
   }

    @Override
    public String getName() {
        return "No More Move";
    }
}
