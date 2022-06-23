package types.websocket;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import types.gameplay.Game;
import types.gameplay.TradeOffer;

public class TradeOfferMessage extends GameMessage {
    private TradeOffer offer;
    
    public TradeOfferMessage() {
        super();
    }
    
    public TradeOfferMessage(TradeOffer offer) {
        super(offer.getSender().getUserId(), offer.getSender().getGameId());
        this.offer = offer;
    }
    
    @JsonGetter
    public TradeOffer getOffer() {
        return this.offer;
    }
    
    @JsonSetter
    public void setOffer(TradeOffer offer) {
        this.offer = offer;
    }
    
    @JsonIgnore
    @Override
    public String toString() {
        return "TRADE OFFER:\n" +
                "\tSender: " + offer.getSender() + "\n" +
                "\tReceiver: " + offer.getReceiver() + "\n" +
                "\tOut: " + offer.getBuyablesOut().toString() + "\n" +
                "\tIn: " + offer.getBuyablesIn().toString() + "\n" +
                "\tBid: " + offer.getNetBid() + "\n";
    }
}
