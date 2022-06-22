package types.rest.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinGameRequest {
    private final String gameId;
    private final String joiningUserPlayerName;
    
    @JsonCreator
    public JoinGameRequest(
            @JsonProperty("gameId") String gameId,
            @JsonProperty("joiningUserPlayerName") String joiningUserPlayerName
    ) {
        this.gameId = gameId;
        this.joiningUserPlayerName = joiningUserPlayerName;
    }
    
    @JsonGetter
    public String getGameId() { return gameId; }
    
    @JsonGetter
    public String getJoiningUserPlayerName() { return joiningUserPlayerName; }
}
