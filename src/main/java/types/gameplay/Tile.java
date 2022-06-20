package types.gameplay;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import types.gameplay.exceptions.*;

import java.util.Objects;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
    defaultImpl = GoToJail.class
)

@JsonSubTypes({
    @JsonSubTypes.Type(value = GoToJail.class, name = "GoToJail"),
    @JsonSubTypes.Type(value = IncomeTax.class, name = "IncomeTax"),
    @JsonSubTypes.Type(value = JustVisiting.class, name = "JustVisiting"),
    @JsonSubTypes.Type(value = Property.class, name = "Property"),
    @JsonSubTypes.Type(value = PublicTransport.class, name = "PublicTransport"),
    @JsonSubTypes.Type(value = StartingPoint.class, name = "StartingPoint"),
})

public interface Tile extends GameEntity {
    /**
     * @return The name of this Tile.
     */
    String getName();

    /**
     * @return The position of this Tile as index value, between 0 and 15.
     */
    int getPosition();
    
    boolean canBeBought();
    
    
    /**
     * Sets the position (i.e. index) of this <code>Tile</code>.
     *
     * @param position The new positions of this <code>Tile</code>. Must be in range [0, 15].
     */
    void setPosition(int position);
    
    /**
     * Handles the event that a player passes through this <code>Tile</code> while advancing on the board.
     *
     * @param player The player passing through this <code>Tile</code>
     */
    void handlePlayerPassBy(Player player);
    
    /**
     * Handles the event that a player lands on this <code>Tile</code> after advancing on the board.
     *
     * @param player The player landing on this <code>Tile</code>
     */
    void handlePlayerStepOn(Player player) throws TileOfTypeNotFoundException;
    
    /**
     * Handles the event when a player tries to buy this <code>Tile</code>.
     *
     * @param player The player trying to buy this <code>Tile</code>.
     * @throws TileNotBuyableException if the <code>Tile</code> is not of type <code>Buyable</code>, or it is already owned
     * @throws NotEnoughMoneyToBuyException if the player does not have enough money to buy this <code>Tile</code>
     */
    void handlePlayerBuy(Player player) throws TileNotBuyableException, NotEnoughMoneyToBuyException;
    
    /**
     * Handles the event when a player tries to sell this <code>Tile</code>.
     *
     * @param player The player trying to sell this <code>Tile</code>.
     * @throws TileNotSellableException if the <code>Tile</code> is not of type <code>Buyable</code>, or it is not owned by the player
     */
    void handlePlayerSell(Player player) throws TileNotSellableException;
    
    @Override
    default boolean is(Object obj) {
        if (obj instanceof Tile t) {
            return this.getGameId().equals(t.getGameId()) && this.getPosition() == t.getPosition();
        }
        return false;
    }

    /**
     * @return The type of this Tile.
     */
    TileType getType();
}
