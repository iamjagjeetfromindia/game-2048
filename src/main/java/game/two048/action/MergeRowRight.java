package game.two048.action;


public class MergeRowRight extends MergeRow {
    @Override
    protected int getInitialPosition(int length) {
        return length - 1;
    }

    @Override
    protected int getStep() {
        return -1;
    }

    @Override
    protected boolean shouldContinueProcessing(int index, int length) {
        return index >= 0;
    }
}
