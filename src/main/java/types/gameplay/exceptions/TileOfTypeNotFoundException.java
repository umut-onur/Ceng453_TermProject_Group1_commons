package types.gameplay.exceptions;

import types.gameplay.TileType;

public class TileOfTypeNotFoundException extends GameplayException {
    public TileOfTypeNotFoundException(TileType type) {
        super("No tile of type " + type.name() + " could be found on the board. Teleport failed.");
    }
}
