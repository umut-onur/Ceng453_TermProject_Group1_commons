package types;

public interface Buyable extends Tile {
    String getName();

    int getFirstCost();

    int getRentForPlayer(Player player);

    Player getOwner();

    void setOwner(Player owner);
}
