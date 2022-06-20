package types.websocket;

import types.gameplay.Buyable;
import types.gameplay.Player;

import java.util.ArrayList;
import java.util.List;

public class TradeOffer extends GameMessage {
    private Player sender;
    private Player receiver;
    private List<Buyable> buyablesIn;
    private List<Buyable> buyablesOut;
    private int netBid;
    
    public TradeOffer(Player sender, Player receiver) {
        super(sender.getUserId(), sender.getGameId());
        this.sender = sender;
        this.receiver = receiver;
        this.buyablesIn = new ArrayList<>();
        this.buyablesOut = new ArrayList<>();
        this. netBid = 0;
    }
    
    public TradeOffer(Player sender, Player receiver, List<Buyable> buyablesIn, List<Buyable> buyablesOut, int netBid) {
        super(sender.getUserId(), sender.getGameId());
        this.sender = sender;
        this.receiver = receiver;
        this.buyablesIn = buyablesIn;
        this.buyablesOut = buyablesOut;
        this. netBid = netBid;
    }
    
    public Player getSender() {
        return this.sender;
    }
    
    public Player getReceiver() {
        return this.receiver;
    }
    
    public List<Buyable> getBuyablesIn() {
        return this.buyablesIn;
    }
    
    public List<Buyable> getBuyablesOut() {
        return this.buyablesOut;
    }
    
    public int getNetBid() {
        return this.netBid;
    }
    
    public void setNetBid(int netBid) {
        this.netBid = netBid;
    }
    
    public boolean isValid() {
        boolean playersAreTogether = this.sender.getGameId().equals(this.receiver.getGameId());
        boolean senderOwnsAllOfferedBuyables = true;
        for (Buyable b : this.buyablesOut) {
            if (!this.sender.ownsBuyable(b)) {
                senderOwnsAllOfferedBuyables = false;
                break;
            }
        }
        boolean receiverOwnsAllDemandedBuyables = true;
        for (Buyable b : this.buyablesIn) {
            if (!this.sender.ownsBuyable(b)) {
                receiverOwnsAllDemandedBuyables = false;
                break;
            }
        }
        return playersAreTogether && senderOwnsAllOfferedBuyables && receiverOwnsAllDemandedBuyables;
    }
}
