package types.gameplay;

public interface Buyable extends Tile {
    String getName();
    
    int getFirstCost();
    
    int getRent();
    
    Player getOwner();
    
    void setOwner(Player owner);
    
    void handlePlayerBuy(Player player);
    void handlePlayerSell(Player player);
}
