package game.two048.rule;

import game.two048.model.Board;

public interface Rule {
    int getOrder();
    boolean evaluate(Board board);
    String getName();
}
