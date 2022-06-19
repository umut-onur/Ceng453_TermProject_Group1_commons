package types.gameplay;

import com.fasterxml.jackson.annotation.JsonTypeName;
import types.gameplay.exceptions.TileNotBuyableException;
import types.gameplay.exceptions.TileNotSellableException;

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
    public void handlePlayerStepOn(Player player) {
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
}
