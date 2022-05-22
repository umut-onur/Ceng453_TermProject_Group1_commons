package types.websocket;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Error {
    public enum ErrorType {
        INTERNAL_ERROR,
        NOT_ENOUGH_MONEY_TO_BUY,
        NOT_ENOUGH_PLAYERS,
        NOT_PLAYERS_TURN,
        TILE_NOT_BUYABLE,
        TILE_NOT_FOUND
    }

    private String description;
    private ErrorType errorType;

    public Error() {}

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
