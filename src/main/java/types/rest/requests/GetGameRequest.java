package types.rest.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetGameRequest {
    private final String gameId;
    
    @JsonCreator
    public GetGameRequest(
            @JsonProperty("gameId") String gameId
    ) {
        this.gameId = gameId;
    }
    
    @JsonGetter
    public String getGameId() { return gameId; }
}
