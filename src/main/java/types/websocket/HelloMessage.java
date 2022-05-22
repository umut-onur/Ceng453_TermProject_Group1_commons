package types.websocket;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.lang.NonNull;

/**
 * HelloMessage is the message sent by the clients upon entering a game, indicating that
 * they have established a websocket connection to the server.
 */
public class HelloMessage {
    private String userId;
    private String gameId;

    public HelloMessage(@NonNull String userId, @NonNull String gameId) {
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
