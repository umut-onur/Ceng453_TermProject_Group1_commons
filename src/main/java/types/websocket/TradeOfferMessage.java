package types.websocket;

import types.gameplay.Game;
import types.gameplay.TradeOffer;

public class TradeOfferMessage extends GameMessage {
    private TradeOffer offer;
    
    public TradeOfferMessage(TradeOffer offer) {
        super(offer.getSender().getUserId(), offer.getSender().getGameId());
        this.offer = offer;
    }
    
    public TradeOffer getOffer() {
        return this.offer;
    }
    
    public void setOffer(TradeOffer offer) {
        this.offer = offer;
    }
}
