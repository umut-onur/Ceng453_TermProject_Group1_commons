package types.exceptions;

public class NotEnoughPlayersException extends GameplayException {
    public NotEnoughPlayersException(int numberOfPlayers) {
        super("There are not enough players to start a game. There must be at least 2 players but there is " + numberOfPlayers + ".");
    }
}

