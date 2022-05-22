package types.gameplay.exceptions;

public class TileNotFoundException extends GameplayException {
    public TileNotFoundException(String partOfName) {
        super("Tile with name containing " + partOfName + " is not found on the board. Teleport failed.");
    }
}
