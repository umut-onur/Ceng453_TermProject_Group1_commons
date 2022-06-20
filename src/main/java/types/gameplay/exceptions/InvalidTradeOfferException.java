package types.gameplay.exceptions;

import types.gameplay.TradeOffer;

public class InvalidTradeOfferException extends GameplayException {
    public InvalidTradeOfferException(TradeOffer offer) {
        super("The trade offer proposed by " + offer.getSender().getName() + " to " + offer.getReceiver().getName() + " is not valid.");
    }
}
