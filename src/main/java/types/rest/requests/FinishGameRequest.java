package types.rest.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class FinishGameRequest {
    private final String id;
    private final Map<String, Integer> playerScores;
    
    
    @JsonCreator
    public FinishGameRequest(
            @JsonProperty("id") String id,
            @JsonProperty("playerScores") Map<String, Integer> playerScores
    ) {
        this.id = id;
        this.playerScores = playerScores;
    }
    
    @JsonGetter
    public String getId() { return id; }
    
    @JsonGetter
    public Map<String, Integer> getPlayerScores() { return playerScores; }
}
