package types.gameplay;

import types.gameplay.exceptions.NotEnoughMoneyToBuyException;
import types.gameplay.exceptions.TileNotBuyableException;
import types.gameplay.exceptions.TileNotSellableException;

public interface Buyable extends Tile {
    /**
     * @return The name of this <code>Buyable</code>
     */
    String getName();
    
    /**
     * @return The assigned price of this <code>Buyable</code>
     */
    int getFirstCost();
    
    /**
     * @return The amount of money that should be paid to the owner of this <code>Buyable</code> when another player steps on it
     */
    int getRent();
    
    /**
     * @return The <code>Player</code> that owns this <code>Buyable</code>, or <code>Null</code> if it is not owned by any
     */
    Player getOwner();
    
    /**
     * Reassigns the owner of this <code>Buyable</code> to the <code>Player</code> passed as a parameter.
     *
     * @param owner The new owner of the <code>Buyable</code>
     */
    void setOwner(Player owner);
    
    @Override
    void handlePlayerBuy(Player player) throws NotEnoughMoneyToBuyException, TileNotBuyableException;
    
    @Override
    void handlePlayerSell(Player player) throws TileNotSellableException;
}
