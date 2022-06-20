package types.websocket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Error {
    public enum ErrorType {
        INTERNAL_ERROR,
        GAME_NOT_FOUND,
        PLAYER_NOT_FOUND,
        GAME_NOT_IN_THIS_PHASE,
        NOT_ENOUGH_MONEY_TO_BUY,
        NOT_ENOUGH_PLAYERS,
        NOT_PLAYERS_TURN,
        TILE_NOT_BUYABLE,
        TILE_NOT_SELLABLE,
        TILE_OF_TYPE_NOT_FOUND,
        INVALID_TRADE_OFFER
    }

    private String description;
    private ErrorType errorType;

    public Error() {}

    @JsonCreator
    public Error(@JsonProperty("description") String description, @JsonProperty("errorType") ErrorType errorType) {
        this.description = description;
        this.errorType = errorType;
    }

    @JsonGetter
    public ErrorType getErrorType() {
        return errorType;
    }

    @JsonGetter
    public String getDescription() {
        return description;
    }

    @JsonSetter
    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    @JsonSetter
    public void setDescription(String description) {
        this.description = description;
    }
}
