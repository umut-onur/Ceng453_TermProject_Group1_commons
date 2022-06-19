package types.gameplay.exceptions;

import types.gameplay.Tile;

public class TileNotSellableException extends TileTradeException {
    public TileNotSellableException(Tile tile) {
        super("Tile with name " + tile.getName() + " is not suitable for selling.");
    }
}
