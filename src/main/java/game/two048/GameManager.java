package game.two048;


import game.two048.action.*;
import game.two048.action.context.RandomFillerContext;
import game.two048.rule.Rule;
import game.two048.rule.RuleEngine;
import lombok.Getter;

import java.util.List;
import java.util.Map;

public class GameManager {
    @Getter
    private final Board board;
    private final RuleEngine ruleEngine = new RuleEngine();
    private final Map<MoveDirection, ActionHandler<Board>> movers;
    private final ActionHandler<RandomFillerContext> randomFillCommand = new RandomFill();


    public enum MoveDirection {
        LEFT, RIGHT, UP, DOWN,RANDOM_FILL
    }

    public enum GameState {
        IN_PROGRESS, WIN, GAME_OVER
    }

    public GameManager(int rows, int cols) {
        this.board = new Board(rows, cols);

        //fill the board with initial values
        this.fillBoard();

        //Register moves
        this.movers = Map.of(
                MoveDirection.LEFT, new MergeRowLeft(),
                MoveDirection.RIGHT, new MergeRowRight(),
                MoveDirection.UP, new MergeColumnUp(),
                MoveDirection.DOWN, new MergeColumnDown()
        );
    }

    public void move(MoveDirection direction) {
        ActionHandler<Board> actionHandler = movers.get(direction);
        actionHandler.execute(this.board);

        // Generate a `2` or `4` at random empty spaces after each merge
        this.randomFillCommand.execute(new RandomFillerContext(this.board, RandomFillerContext.FillType.TWO_AND_FOUR, false));

    }

   public GameState getGameState() {
       List<Rule> appliedRules = this.ruleEngine.evaluate(this.board);

       if (appliedRules.isEmpty()) {
           return GameState.IN_PROGRESS;
       }

       if (appliedRules.stream().anyMatch(rule -> rule.getName().equals("Goal of 2048"))) {
           return GameState.WIN;
       } else if (appliedRules.stream().anyMatch(rule -> rule.getName().equals("No More Move"))) {
           return GameState.GAME_OVER;
       }
       return GameState.IN_PROGRESS;
   }


    private void fillBoard() {
        this.randomFillCommand.execute(new RandomFillerContext(this.board, RandomFillerContext.FillType.TWO_AND_NULL, true));
    }

}
