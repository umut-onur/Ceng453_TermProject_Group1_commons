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

    /**
     * Initializes the GameResponse with a Game.
     */
    public GameResponse(Game game) {
        this.game = game;
    }

    /**
     * Initializes the GameResponse with a HelloResponse.
     */
    public GameResponse(HelloResponse helloResponse) {
        this.helloResponse = helloResponse;
    }

    @JsonGetter
    public Game getGame() {
        return game;
    }

    @JsonGetter
    public HelloResponse getHelloResponse() {
        return helloResponse;
    }

    @JsonSetter
    public void setGame(Game game) {
        this.game = game;
    }

    @JsonSetter
    public void setHelloResponse(HelloResponse helloResponse) {
        this.helloResponse = helloResponse;
    }

    /**
     * @return The type of this message based on its internal fields.
     */
    public GameMessageType getMessageType() {
        if (this.game == null && this.helloResponse == null) {
            return GameMessageType.Invalid;
        }
        if (this.game != null) {
            return GameMessageType.GameState;
        }
        return GameMessageType.Hello;
    }
}
