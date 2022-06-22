package types.rest.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateGameRequest {
    private final String hostUserPlayerName;
    
    @JsonCreator
    public CreateGameRequest(
            @JsonProperty("hostUserPlayerName") String hostUserPlayerName
    ) {
        this.hostUserPlayerName = hostUserPlayerName;
    }
    
    @JsonGetter
    public String getHostUserPlayerName() { return hostUserPlayerName; }
}
