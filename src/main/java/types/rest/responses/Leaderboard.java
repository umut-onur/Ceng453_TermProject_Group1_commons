package types.rest.responses;

import types.gameplay.LeaderboardEntry;

import java.util.List;

public class Leaderboard {
    private final List<LeaderboardEntry> leaderboard;
    
    public Leaderboard(List<LeaderboardEntry> leaderboard) {
        this.leaderboard = leaderboard;
    }
    
    public List<LeaderboardEntry> getLeaderboard() {
        return this.leaderboard;
    }
    
    public void addEntryToLeaderboard(LeaderboardEntry leaderboardEntry) {
        this.leaderboard.add(leaderboardEntry);
    }
}
