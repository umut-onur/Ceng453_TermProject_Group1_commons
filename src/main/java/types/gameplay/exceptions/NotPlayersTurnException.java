package types.gameplay.exceptions;

public class NotPlayersTurnException extends GameplayException {
    public NotPlayersTurnException(String playerName) {
        super("It's currently not " + playerName + "'s turn.");
    }
}
