package types.gameplay;

public class LeaderboardEntry {
    private String userId;
    private String userName;
    private int score;
    
    public LeaderboardEntry() {
    
    }
    
    public LeaderboardEntry(String userId, String userName, int score) {
        this.userId = userId;
        this.userName = userName;
        this.score = score;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public Integer getScore() {
        return score;
    }
}
