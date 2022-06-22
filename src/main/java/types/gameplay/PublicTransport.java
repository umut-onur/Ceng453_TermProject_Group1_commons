package types.gameplay;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import types.gameplay.exceptions.NotEnoughMoneyToBuyException;
import types.gameplay.exceptions.TileNotBuyableException;

@JsonTypeName("PublicTransport")
public class PublicTransport implements Buyable {
    private Game game;
    private String name;
    private int firstCost;
    private Player owner;
    private int position;
    
    public PublicTransport() {}

    public PublicTransport(Game game, String name, int firstCost, int position) {
        this.game = game;
        this.name = name;
        this.firstCost = firstCost;
        this.owner = null;
        this.position = position;
    }
    
    public PublicTransport(Game game, String name, int firstCost) {
        this.game = game;
        this.name = name;
        this.firstCost = firstCost;
        this.owner = null;
        this.position = -99;
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
    public int getFirstCost() {
        return firstCost;
    }
    
    @Override
    public boolean canBeBought() {
        return this.owner == null;
    }
    
    @Override
    public int getRent() {
        return (this.owner != null) ? (firstCost / 10) * this.owner.numberOfPublicTransportsOwned() : (firstCost / 10);
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
    public void handlePlayerBuy(Player player) throws NotEnoughMoneyToBuyException, TileNotBuyableException {
        if (!this.canBeBought()) {
            throw new TileNotBuyableException(this);
        }
        if (player.getBalance() < this.getFirstCost()) {
            throw new NotEnoughMoneyToBuyException(player, this);
        }
        player.pay(this.getFirstCost());
        this.owner = player;
    }
    
    @Override
    public void handlePlayerSell(Player player) {
        player.earn(this.getFirstCost());
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
        }
    }

    @Override
    public TileType getType() {
        return TileType.PUBLIC_TRANSPORT;
    }
}
