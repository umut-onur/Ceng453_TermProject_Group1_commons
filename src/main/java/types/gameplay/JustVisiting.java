package types.gameplay;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import types.gameplay.exceptions.TileNotBuyableException;
import types.gameplay.exceptions.TileNotSellableException;

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
    
    @JsonIgnore
    @Override
    public String getGameId() {
        return this.game != null ? game.getId() : null;
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
    public void handlePlayerStepOn(Player player) {}
    
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
        return TileType.JUST_VISITING;
    }
}
