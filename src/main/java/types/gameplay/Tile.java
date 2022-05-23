package types.gameplay;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import types.gameplay.exceptions.NotEnoughMoneyToBuyException;
import types.gameplay.exceptions.TileNotBuyableException;

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
    String getName();
    
    int getPosition();
    
    void setPosition(int position);
    
    void handlePlayerPassBy(Player player);
    
    void handlePlayerStepOn(Player player);
    
    void handlePlayerBuy(Player player) throws TileNotBuyableException, NotEnoughMoneyToBuyException;
    
    void handlePlayerSell(Player player) throws TileNotBuyableException;
    
    @Override
    default boolean is(Object obj) {
        if (obj instanceof Tile t) {
            return this.getGameId().equals(t.getGameId()) && this.getPosition() == t.getPosition();
        }
        return false;
    }
}
