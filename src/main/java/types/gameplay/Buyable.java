package types.gameplay;

import types.gameplay.exceptions.NotEnoughMoneyToBuyException;

public interface Buyable extends Tile {
    String getName();
    
    int getFirstCost();
    
    int getRent();
    
    Player getOwner();
    
    void setOwner(Player owner);
    
    @Override
    void handlePlayerBuy(Player player) throws NotEnoughMoneyToBuyException;
    
    @Override
    void handlePlayerSell(Player player);
}
