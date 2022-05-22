package types.gameplay;

import com.fasterxml.jackson.annotation.JsonTypeName;

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
        return (this.owner != null) ? (firstCost / 10) * this.owner.getPublicTransportsOwned() : (firstCost / 10);
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
    public void handlePlayerBuy(Player player) {
        this.owner = player;
        player.incrementPublicTransportsOwned();
    }
    
    public void handlePlayerSell(Player player) {
        this.owner = null;
        player.decrementPublicTransportsOwned();
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
}
