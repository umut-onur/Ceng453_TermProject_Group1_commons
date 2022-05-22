package types.gameplay;

public class LeaderboardEntry {
    private String userId;
    private String userName;
    private int score;
    
    public LeaderboardEntry(String userId, String userName, int score) {
        this.userId = userId;
        this.userName = userName;
        this.score = score;
    }
    
    public Integer getScore() {
        return score;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getUserName() {
        return userName;
    }
}
