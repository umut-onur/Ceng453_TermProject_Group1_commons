package types.gameplay;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import types.gameplay.exceptions.TileNotBuyableException;
import types.gameplay.exceptions.TileNotSellableException;

@JsonTypeName("IncomeTax")
public class IncomeTax implements Tile {
    private Game game;
    private String name;
    private int position;
    private int taxAmount;
    
    public IncomeTax() {}
    
    public IncomeTax(Game game, int position) {
        this.game = game;
        this.name = "Income Tax";
        this.position = position;
        this.taxAmount = 50;
    }
    
    public IncomeTax(Game game) {
        this.game = game;
        this.name = "Income Tax";
        this.position = -99;
        this.taxAmount = 50;
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
}
