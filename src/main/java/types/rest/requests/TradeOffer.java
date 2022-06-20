package types.rest.requests;

import types.gameplay.Buyable;
import types.gameplay.Player;

import java.util.ArrayList;
import java.util.List;

public class TradeOffer {
    private Player sender;
    private Player receiver;
    private List<Buyable> buyablesIn;
    private List<Buyable> buyablesOut;
    private int netBid;
    
    public TradeOffer(Player sender, Player receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.buyablesIn = new ArrayList<>();
        this.buyablesOut = new ArrayList<>();
        this. netBid = 0;
    }
    
    public TradeOffer(Player sender, Player receiver, List<Buyable> buyablesIn, List<Buyable> buyablesOut, int netBid) {
        this.sender = sender;
        this.receiver = receiver;
        this.buyablesIn = buyablesIn;
        this.buyablesOut = buyablesOut;
        this. netBid = netBid;
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
}
