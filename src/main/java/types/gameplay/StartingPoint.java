package types.gameplay;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("StartingPoint")
public class StartingPoint implements Tile {
    private Game game;
    private String name;
    private int position;
    private int passingReward;
    
    public StartingPoint() {}
    
    public StartingPoint(Game game) {
        this.game = game;
        this.name = "Starting Point";
        this.position = 0;
        this.passingReward = 100;
    }
    
    @Override
    public String getGameId() {
        return game.getId();
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public int getPosition() {
        return position;
    }
    
    public int getPassingReward() {
        return passingReward;
    }
    
    @Override
    public void setPosition(int position) {
        this.position = position;
    }
    
    @Override
    public void handlePlayerPassBy(Player player) {
        player.earn(this.passingReward);
    }
    
    @Override
    public void handlePlayerStepOn(Player player) {}
}
