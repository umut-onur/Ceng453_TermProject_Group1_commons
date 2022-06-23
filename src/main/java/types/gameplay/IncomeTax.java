package types.gameplay;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import types.gameplay.exceptions.TileNotBuyableException;
import types.gameplay.exceptions.TileNotSellableException;

@JsonTypeName("IncomeTax")
public class IncomeTax implements Tile {
    private Game game;
    private String gameId;
    private String name;
    private int position;
    private int taxAmount;
    
    public IncomeTax() {}
    
    public IncomeTax(Game game, int position) {
        this.game = game;
        this.gameId = game.getId();
        this.name = "Income Tax";
        this.position = position;
        this.taxAmount = 50;
    }
    
    public IncomeTax(Game game) {
        this.game = game;
        this.gameId = game.getId();
        this.name = "Income Tax";
        this.position = -99;
        this.taxAmount = 50;
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
    
    public int getTaxAmount() {
        return taxAmount;
    }
    
    @Override
    public void setPosition(int position) {
        this.position = position;
    }
    
    @Override
    public void handlePlayerPassBy(Player player) {}
    
    @Override
    public void handlePlayerStepOn(Player player) {
        player.pay(this.taxAmount);
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
        return TileType.INCOME_TAX;
    }
    
    @JsonIgnore
    @Override
    public String toString() {
        return "'" + this.name + "' (-" + this.taxAmount + "$)";
    }
}
