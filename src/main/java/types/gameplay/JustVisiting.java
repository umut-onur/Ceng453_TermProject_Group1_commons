package types.gameplay;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("JustVisiting")
public class JustVisiting implements Tile {
    private Game game;
    private String name;
    private int position;
    
    public JustVisiting() {}
    
    public JustVisiting(Game game) {
        this.game = game;
        this.name = "Jail/Just Visiting";
        this.position = 12;
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
    public void handlePlayerStepOn(Player player) {}
}