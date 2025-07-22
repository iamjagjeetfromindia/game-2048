package game.two048.rule;

import game.two048.Board;

import static game.two048.util.Constants.WINNING_VALUE;

public class Two048Rule implements Rule{

    @Override
    public int getOrder() {
        return 1;
    }


   @Override
   public boolean evaluate(Board board) {
       return board.getCells().stream()
               .anyMatch(cell -> cell.getValue() != null && cell.getValue() == WINNING_VALUE);
   }

    @Override
    public String getName() {
        return "Goal of 2048";
    }
}
