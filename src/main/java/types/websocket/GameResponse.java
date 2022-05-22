package types.websocket;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import types.gameplay.Game;

/**
 * GameResponse is the top-level message transmitted from server-to-client in a websocket communication.
 * It must either have <code>game</code> or <code>helloResponse</code> fields set. It cannot have both.
 */
public class GameResponse {
    private Game game;                      // the updated game state that server sends to the client
    private HelloResponse helloResponse;    // the response of the hello protocol at the beginning of a websocket communication

    private Error error;                    // the error message if user command was not successful

    public GameResponse() {}

    /**
     * Initializes the GameResponse with a <code>Game</code>.
     */
    public GameResponse(Game game) {
        this.game = game;
    }

    /**
     * Initializes the GameResponse with a <code>HelloResponse</code>.
     */
    public GameResponse(HelloResponse helloResponse) {
        this.helloResponse = helloResponse;
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
    public HelloResponse getHelloResponse() {
        return helloResponse;
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
    public void setHelloResponse(HelloResponse helloResponse) {
        this.helloResponse = helloResponse;
    }

    @JsonSetter
    public void setError(Error error) {
        this.error = error;
    }

    /**
     * @return The type of this message based on its internal fields.
     */
    public GameMessageType getMessageType() {
        if (this.game != null && this.helloResponse == null && this.error == null) {
            return GameMessageType.GameState;
        }
        if (this.game == null && this.helloResponse != null && this.error == null) {
            return GameMessageType.Hello;
        }
        if (this.game == null && this.helloResponse == null && this.error != null) {
            return GameMessageType.Error;
        }
        return GameMessageType.Invalid;
    }
}
