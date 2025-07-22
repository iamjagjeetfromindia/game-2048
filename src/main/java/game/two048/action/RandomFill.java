package game.two048.action;

import game.two048.model.Cell;
import game.two048.action.context.RandomFillerContext;

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
                if(Math.random() < 0.1)
                {
                    cell.setValue(this.getRandomValue(context.getFillType()));
                }
            }
        }

    }

    private Integer getRandomValue(RandomFillerContext.FillType fillType) {
       if(fillType == RandomFillerContext.FillType.TWO_AND_NULL) {
            return Math.random() < 0.1 ? 2 : null;
        } else if(fillType == RandomFillerContext.FillType.TWO_AND_FOUR) {
           return Math.random() < 0.5 ? 2 : 4;
        } else if(fillType == RandomFillerContext.FillType.FOUR_AND_NULL) {
            return Math.random() < 0.5 ? 4 : null;
        }
        return null; // Default case, should not happen
    }
}
