package types.websocket;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import types.gameplay.Game;

/**
 * GameResponse is the top-level message transmitted from server-to-client in a websocket communication.
 * It must either have <code>game</code> or <code>error</code> fields set. It cannot have both.
 */
public class GameResponse {
    private Game game;                      // the updated game state that server sends to the client
    private Error error;                    // the error message if user command was not successful

    public GameResponse() {}

    /**
     * Initializes the GameResponse with a <code>Game</code>.
     */
    public GameResponse(Game game) {
        this.game = game;
    }

    /**
     * Initializes the GameResponse with an <code>Error</code>.
     */
    public GameResponse(Error error) {
        this.error = error;
    }

    @JsonGetter
    public Game getGame() {
        return game;
    }

    @JsonGetter
    public Error getError() {
        return error;
    }

    @JsonSetter
    public void setGame(Game game) {
        this.game = game;
    }

    @JsonSetter
    public void setError(Error error) {
        this.error = error;
    }

    /**
     * @return The type of this message based on its internal fields.
     */
    public GameMessageType getMessageType() {
        if (this.game == null && this.error == null) {
            return GameMessageType.Invalid;
        }
        if (this.game != null) {
            return GameMessageType.GameState;
        }
        return GameMessageType.Error;
    }
}
