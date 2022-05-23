package types.gameplay;

public interface Buyable extends Tile {
    String getName();
    
    int getFirstCost();
    
    int getRent();
    
    Player getOwner();
    
    void setOwner(Player owner);
    
    @Override
    void handlePlayerBuy(Player player);
    
    @Override
    void handlePlayerSell(Player player);
}
