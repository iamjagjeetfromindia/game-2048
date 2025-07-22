package game.two048.action;

public interface ActionHandler<T> {

    void execute(T context);
}
