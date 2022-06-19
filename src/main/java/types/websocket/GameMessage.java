package types.websocket;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * GameMessage is the top-level message transmitted from client-to-server in a websocket communication.
 * It only contains the id of the player and id of the game.
 */
public class GameMessage {
    private String userId;
    private String gameId;

    public GameMessage() {
        this("", "");
    }

    public GameMessage(@JsonProperty("userId") String userId, @JsonProperty("gameId") String gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    @JsonGetter
    public String getUserId() {
        return userId;
    }

    @JsonGetter
    public String getGameId() {
        return gameId;
    }

    @JsonSetter
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonSetter
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
