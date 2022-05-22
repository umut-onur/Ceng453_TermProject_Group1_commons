package types.gameplay.exceptions;

public class TileNotBuyableException extends GameplayException {
    public TileNotBuyableException(String tileName) {
        super("Tile with name " + tileName + " is not suitable for purchase.");
    }
}
