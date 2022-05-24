package types.rest.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StartGameRequest {
    private final String gameId;
    
    @JsonCreator
    public StartGameRequest(
            @JsonProperty("gameId") String gameId
    ) {
        this.gameId = gameId;
    }
    
    @JsonGetter
    public String getGameId() { return gameId; }
}
