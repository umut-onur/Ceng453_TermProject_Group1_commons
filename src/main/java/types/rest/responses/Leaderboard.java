package types.rest.responses;

import types.gameplay.LeaderboardEntry;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard {
    private List<LeaderboardEntry> leaderboard = new ArrayList<LeaderboardEntry>();
    public Leaderboard() {
    }
    
    public void setLeaderboard(List<LeaderboardEntry> leaderboard) {
        this.leaderboard = leaderboard;
    }
    
    public List<LeaderboardEntry> getLeaderboard() {
        return this.leaderboard;
    }
    
    public void addEntryToLeaderboard(LeaderboardEntry leaderboardEntry) {
        this.leaderboard.add(leaderboardEntry);
    }
}
