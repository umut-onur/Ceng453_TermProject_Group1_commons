package types.gameplay;

import types.gameplay.exceptions.NotEnoughMoneyToBuyException;
import types.gameplay.exceptions.TileNotBuyableException;
import types.gameplay.exceptions.TileNotSellableException;

public interface Buyable extends Tile {
    String getName();
    
    int getFirstCost();
    
    int getRent();
    
    Player getOwner();
    
    void setOwner(Player owner);
    
    @Override
    void handlePlayerBuy(Player player) throws NotEnoughMoneyToBuyException, TileNotBuyableException;
    
    @Override
    void handlePlayerSell(Player player) throws TileNotSellableException;
}
