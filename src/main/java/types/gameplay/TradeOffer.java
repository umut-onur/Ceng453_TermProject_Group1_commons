package types.gameplay;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import types.gameplay.Buyable;
import types.gameplay.Player;
import types.websocket.GameMessage;
import types.websocket.TradeOfferMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class TradeOffer implements GameEntity{
    private Player sender;
    private Player receiver;
    private List<Buyable> buyablesIn;
    private List<Buyable> buyablesOut;
    private int netBid;
    
    public TradeOffer() {
        this.sender = null;
        this.receiver = null;
        this.buyablesIn = new ArrayList<>();
        this.buyablesOut = new ArrayList<>();
        this.netBid = 0;
    }
    public TradeOffer(Player sender, Player receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.buyablesIn = new ArrayList<>();
        this.buyablesOut = new ArrayList<>();
        this.netBid = 0;
    }
    
    public TradeOffer(Player sender, Player receiver, List<Buyable> buyablesIn, List<Buyable> buyablesOut, int netBid) {
        this.sender = sender;
        this.receiver = receiver;
        this.buyablesIn = buyablesIn;
        this.buyablesOut = buyablesOut;
        this.netBid = netBid;
    }
    
    @JsonGetter
    public Player getSender() {
        return this.sender;
    }
    
    @JsonGetter
    public Player getReceiver() {
        return this.receiver;
    }
    
    @JsonGetter
    public List<Buyable> getBuyablesIn() {
        return this.buyablesIn;
    }
    
    @JsonGetter
    public List<Buyable> getBuyablesOut() {
        return this.buyablesOut;
    }
    
    @JsonGetter
    public int getNetBid() {
        return this.netBid;
    }
    
    @JsonSetter
    public void setSender(Player sender) {
        this.sender = sender;
    }
    
    @JsonSetter
    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }
    
    @JsonSetter
    public void setBuyablesOut(List<Buyable> buyablesOut) {
        this.buyablesOut = buyablesOut;
    }
    
    @JsonSetter
    public void setBuyablesIn(List<Buyable> buyablesIn) {
        this.buyablesIn = buyablesIn;
    }
    
    @JsonSetter
    public void setNetBid(int netBid) {
        this.netBid = netBid;
    }
    
    @JsonIgnore
    public boolean isValid() {
        boolean playersAreTogether = Objects.equals(this.sender.getGameId(), this.receiver.getGameId());
        boolean senderOwnsAllOfferedBuyables = true;
        for (Buyable b : this.buyablesOut) {
            if (!this.sender.ownsBuyable(b)) {
                senderOwnsAllOfferedBuyables = false;
                break;
            }
        }
        boolean receiverOwnsAllDemandedBuyables = true;
        for (Buyable b : this.buyablesIn) {
            if (!this.receiver.ownsBuyable(b)) {
                receiverOwnsAllDemandedBuyables = false;
                break;
            }
        }
        return playersAreTogether && senderOwnsAllOfferedBuyables && receiverOwnsAllDemandedBuyables;
    }
    
    @JsonIgnore
    @Override
    public String getGameId() {
        return this.sender.getGameId();
    }
    
    @JsonIgnore
    public boolean equals(Object obj) {
        if (obj instanceof TradeOffer o) {
            return this.sender.is(o.sender) &&
                    this.receiver.is(o.receiver) &&
                    new HashSet<>(this.buyablesIn).equals(new HashSet<>(o.buyablesIn)) &&
                    new HashSet<>(this.buyablesOut).equals(new HashSet<>(o.buyablesIn)) &&
                    this.netBid == o.netBid;
        }
        return false;
    }
}
