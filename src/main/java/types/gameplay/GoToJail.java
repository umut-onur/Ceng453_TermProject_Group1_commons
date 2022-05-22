package types.gameplay;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("GoToJail")
public class GoToJail implements Tile {
    private Game game;
    private String name;
    private int position;
    
    public GoToJail() {}
    
    public GoToJail(Game game) {
        this.game = game;
        this.name = "Go to Jail";
        this.position = 4;
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
    
    @Override
    public void setPosition(int position) {
        this.position = position;
    }
    
    @Override
    public void handlePlayerPassBy(Player player) {}
    
    @Override
    public void handlePlayerStepOn(Player player) {
        player.sendToJail();
    }
}