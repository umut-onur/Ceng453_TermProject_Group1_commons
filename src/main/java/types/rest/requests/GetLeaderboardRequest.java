package types.rest.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetLeaderboardRequest {
    public enum LeaderboardDuration {
        week,
        month,
        all
    }
    
    private final LeaderboardDuration leaderboardDuration;
    
    @JsonCreator
    public GetLeaderboardRequest(
            @JsonProperty("leaderboardDuration") LeaderboardDuration leaderboardDuration
    ) {
        this.leaderboardDuration = leaderboardDuration;
    }
    
    @JsonGetter
    public LeaderboardDuration getLeaderboardDuration() { return leaderboardDuration; }
}
