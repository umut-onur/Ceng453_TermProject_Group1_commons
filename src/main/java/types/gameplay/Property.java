package types.gameplay;

import com.fasterxml.jackson.annotation.JsonTypeName;
import types.gameplay.exceptions.NotEnoughMoneyToBuyException;

import java.io.NotActiveException;

@JsonTypeName("Property")
public class Property implements Buyable {
    private Game game;
    private String name;
    private int firstCost;
    private Player owner;
    private int position;

    public Property() {}
    
    public Property(Game game, String name, int firstCost, int position) {
        this.game = game;
        this.name = name;
        this.firstCost = firstCost;
        this.owner = null;
        this.position = position;
    }
    
    public Property(Game game, String name, int firstCost) {
        this.game = game;
        this.name = name;
        this.firstCost = firstCost;
        this.owner = null;
        this.position = -99;
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
    public int getFirstCost() {
        return firstCost;
    }
    
    @Override
    public int getRent() {
        return firstCost / 10;
    }
    
    @Override
    public Player getOwner() {
        return owner;
    }
    
    @Override
    public void setOwner(Player owner) {
        this.owner = owner;
    }
    
    @Override
    public void handlePlayerBuy(Player player) throws NotEnoughMoneyToBuyException {
        if (player.getBalance() < this.getFirstCost()) {
            throw new NotEnoughMoneyToBuyException(player, this);
        }
        player.pay(this.getFirstCost());
        player.acquireProperty(this);
        this.owner = player;
    }
    
    @Override
    public void handlePlayerSell(Player player) {
        player.earn(this.getFirstCost());
        player.releaseProperty(this);
        this.owner = null;
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
        if (this.owner != null) {
            player.pay(this.getRent(), this.owner);
            player.isOnUnownedBuyable = false;
        } else {
            player.isOnUnownedBuyable = true;
        }
    }

    @Override
    public TileType getType() {
        return TileType.PROPERTY;
    }
}
