package types.gameplay.exceptions;

import types.gameplay.Tile;

public class TileNotBuyableException extends TileTradeException{
    public TileNotBuyableException(Tile tile) {
        super("Tile with name " + tile.getName() + " is not suitable for purchase.");
    }
}
