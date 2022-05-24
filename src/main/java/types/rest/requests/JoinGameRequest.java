package types.rest.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinGameRequest {
    private final String gameId;
    
    @JsonCreator
    public JoinGameRequest(
            @JsonProperty("gameId") String gameId
    ) {
        this.gameId = gameId;
    }
    
    @JsonGetter
    public String getGameId() { return gameId; }
}
