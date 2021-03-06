package types.gameplay;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import types.gameplay.exceptions.TileNotBuyableException;
import types.gameplay.exceptions.TileNotSellableException;
import types.gameplay.exceptions.TileOfTypeNotFoundException;

@JsonTypeName("GoToJail")
public class GoToJail implements Tile {
    private Game game;
    private String gameId;
    private String name;
    private int position;
    
    public GoToJail() {}
    
    public GoToJail(Game game) {
        this.game = game;
        this.gameId = game.getId();
        this.name = "Go to Jail";
        this.position = 4;
    }
    
    @JsonGetter
    @Override
    public String getGameId() {
        return this.gameId;
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
    public boolean canBeBought() {
        return false;
    }
    
    @Override
    public void setPosition(int position) {
        this.position = position;
    }
    
    @Override
    public void handlePlayerPassBy(Player player) {}
    
    @Override
    public void handlePlayerStepOn(Player player) throws TileOfTypeNotFoundException {
        player.sendToJail();
    }
    
    @Override
    public void handlePlayerBuy(Player player) throws TileNotBuyableException {
        throw new TileNotBuyableException(this);
    }
    
    @Override
    public void handlePlayerSell(Player player) throws TileNotSellableException {
        throw new TileNotSellableException(this);
    }

    @Override
    public TileType getType() {
        return TileType.GO_TO_JAIL;
    }
    
    @JsonIgnore
    @Override
    public String toString() {
        return "'" + this.name + "' (2 Turns)";
    }
}
