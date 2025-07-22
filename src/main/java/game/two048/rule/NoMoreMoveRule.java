package game.two048.rule;

import game.two048.model.Board;

import static game.two048.util.Constants.RULE_NO_MORE_MOVE;

public class NoMoreMoveRule implements Rule{
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
        return RULE_NO_MORE_MOVE;
    }
}
