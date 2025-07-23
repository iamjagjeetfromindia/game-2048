package game.two048.action;

import game.two048.model.Cell;
import game.two048.action.context.RandomFillerContext;

import static game.two048.util.Constants.*;

public class RandomFill implements ActionHandler<RandomFillerContext> {
    @Override
    public void execute(RandomFillerContext context) {

        // fill cell as per context
       for(Cell cell : context.getBoard().getCells()) {
            if (cell.getValue() == null && context.isFillAllCells()) {
                cell.setValue(this.getRandomValue(context.getFillType()));
            }
            else if(cell.getValue() == null && !context.isFillAllCells())
            {
                //randomly fill some cell
                if(Math.random() < PROBABILITY_CELL_FILL_AFTER_MOVE)
                {
                    cell.setValue(this.getRandomValue(context.getFillType()));
                }
            }
        }

    }

    private Integer getRandomValue(RandomFillerContext.FillType fillType) {
       if(fillType == RandomFillerContext.FillType.TWO_AND_NULL) {
            return Math.random() < PROBABILITY_FILL_2_IN_2_OR_NULL ? 2 : null;
        } else if(fillType == RandomFillerContext.FillType.TWO_AND_FOUR) {
           return Math.random() < PROBABILITY_FILL_2_IN_2_OR_4 ? 2 : 4;
        }
        return null; // Default case, should not happen
    }
}
