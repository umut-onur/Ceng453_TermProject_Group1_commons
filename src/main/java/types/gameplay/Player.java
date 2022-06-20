package types.gameplay;

import com.fasterxml.jackson.annotation.JsonIgnore;
import types.auth.User;
import types.gameplay.exceptions.*;
import types.websocket.TradeOfferMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player implements GameEntity {
    private Game game;
    private String userId;
    private String name;
    private int balance;
    private int doubleRollStreak;
    private int turnsInJailLeft;
    private int position;
    private Dice mostRecentDice;
    
    public Player() {
        this.game = null;
        this.userId = null;
        this.name = null;
        this.balance = 1500;
        this.doubleRollStreak = 0;
        this.turnsInJailLeft = 0;
        this.position = 0;
        this.mostRecentDice = null;
    }

    public Player(Game game, String userId, String name) {
        this.game = game;
        this.userId = userId;
        this.name = name;
        this.balance = 1500;
        this.doubleRollStreak = 0;
        this.turnsInJailLeft = 0;
        this.position = 0;
        this.mostRecentDice = this.game.getDice();
    }

    public Player(Game game, String name) {
        this.game = game;
        this.userId = null;
        this.name = name;
        this.balance = 1500;
        this.doubleRollStreak = 0;
        this.turnsInJailLeft = 0;
        this.position = 0;
        this.mostRecentDice = this.game.getDice();
    }

    public Player(Game game, User user) {
        this.game = game;
        this.userId = user.getId();
        this.name = user.getUsername();
        this.balance = 1500;
        this.doubleRollStreak = 0;
        this.turnsInJailLeft = 0;
        this.position = 0;
        this.mostRecentDice = this.game.getDice();
    }

    public Player(Game game, User user, String name) {
        this.game = game;
        this.userId = user.getId();
        this.name = name;
        this.balance = 1500;
        this.doubleRollStreak = 0;
        this.turnsInJailLeft = 0;
        this.position = 0;
        this.mostRecentDice = this.game.getDice();
    }

    @Override
    public String getGameId() {
        return game.getId();
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public int getDoubleRollStreak() {
        return doubleRollStreak;
    }

    public int getTurnsInJailLeft() {
        return turnsInJailLeft;
    }

    @JsonIgnore
    public boolean isOnUnownedBuyable() {
        return this.game.getBoard().get(this.getPosition()).canBeBought();
    }
    
    public List<Buyable> buyables() {
        List<Buyable> buyables = new ArrayList<>();
        for (Tile t : this.game.getBoard()) {
            if (t.getType().equals(TileType.PROPERTY) || t.getType().equals(TileType.PUBLIC_TRANSPORT)) {
                Buyable b = (Buyable) t;
                if (this.ownsBuyable(b)) {
                    buyables.add(b);
                }
            }
        }
        return buyables;
    }
    
    public int numberOfPublicTransportsOwned() {
        int numberOfPublicTransportsOwned = 0;
        for (Buyable b : this.buyables()) {
            if (b.getType().equals(TileType.PUBLIC_TRANSPORT)) {
                numberOfPublicTransportsOwned++;
            }
        }
        return numberOfPublicTransportsOwned;
    }
    
    public boolean ownsBuyable(Buyable buyable) {
        return buyable.getOwner() != null && this.is(buyable.getOwner());
    }

    public int getPosition() {
        return position;
    }

    public int getScore() {
        int score = this.balance;
        for (Buyable b : this.buyables()) {
            score += b.getFirstCost();
        }
        return score;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @JsonIgnore
    public void setGame(Game game) {
        this.game = game;
    }

    public void incrementDoubleRollStreak() {
        this.doubleRollStreak += 1;
    }

    public void resetDoubleRollStreak() {
        this.doubleRollStreak = 0;
    }

    public void decrementTurnsInJailLeft() {
        this.turnsInJailLeft -= 1;
    }

    public void sendToJail() throws TileOfTypeNotFoundException {
        this.position = this.game.findFirstTileOfType(TileType.JUST_VISITING);
        this.turnsInJailLeft = 2;
    }
    
    public Dice getMostRecentDice() {
        return mostRecentDice;
    }
    
    public boolean willRepeatTurn() {
        return this.mostRecentDice.isDoubleDice();
    }
    
    public void rollDice() {
        this.game.getDice().roll();
        this.mostRecentDice = this.game.getDice();
    }
    public void handleDice() throws TileOfTypeNotFoundException {
        if (this.getMostRecentDice().isDoubleDice()) {
            this.doubleRollStreak++;
            if (this.doubleRollStreak == 3) {
                this.doubleRollStreak = 0;
                this.sendToJail();
            } else {
                advancePawn(this.getMostRecentDice().getTotal());
            }
        } else {
            this.doubleRollStreak = 0;
            advancePawn(this.getMostRecentDice().getTotal());
        }
    }
    private void advancePawn(int steps) throws TileOfTypeNotFoundException {
        for (int i=0; i<steps; i++) {
            this.position = ((this.position + 1) % 16);
            this.passByTile(this.game.getBoard().get(this.position));
        }
        this.stepOnTile(this.game.getBoard().get(this.position));
        this.game.setPhase(GamePhase.trade);
    }
    
    public void buy() throws NotEnoughMoneyToBuyException, TileNotBuyableException {
        Tile tile = this.game.getBoard().get(this.position);
        tile.handlePlayerBuy(this);
    }
    
    public void passByTile(Tile tile) {
        tile.handlePlayerPassBy(this);
    }
    
    public void stepOnTile(Tile tile) throws TileOfTypeNotFoundException {
        tile.handlePlayerStepOn(this);
    }
    
    public void pay(int amount) {
        this.balance -= amount;
    }
    
    public void pay(int amount, Player player) {
        this.balance -= amount;
        player.balance += amount;
    }
    
    public void offerTrade(TradeOffer offer) throws InvalidTradeOfferException {
        if (!offer.isValid()) {
            throw new InvalidTradeOfferException(offer);
        }
        this.game.setCurrentOffer(offer);
        this.game.setCurrentPlayer(offer.getReceiver());
        this.game.setPhase(GamePhase.reply);
    }
    
    public void offerTrade(Player otherPlayer, List<Buyable> incomingBuyables, List<Buyable> outgoingBuyables, int netBid) throws InvalidTradeOfferException {
        TradeOffer offer = new TradeOffer(this, otherPlayer,incomingBuyables, outgoingBuyables, netBid);
        this.offerTrade(offer);
    }
    
    public void acceptTradeOffer() throws TileNotSellableException, TileNotBuyableException, InvalidTradeOfferException {
        if (!this.game.getCurrentOffer().isValid() || !this.game.getCurrentOffer().getReceiver().is(this)) {
            throw new InvalidTradeOfferException(this.game.getCurrentOffer());
        }
        this.tradeWithPlayer(this.game.getCurrentOffer().getReceiver(), this.game.getCurrentOffer().getBuyablesIn(), this.game.getCurrentOffer().getBuyablesOut(), this.game.getCurrentOffer().getNetBid());
        this.game.setCurrentOffer(null);
        this.game.setCurrentPlayer(this.game.getCurrentOffer().getSender());
        this.game.setPhase(GamePhase.trade);
    }
    
    public void rejectTradeOffer() throws InvalidTradeOfferException {
        if (!this.game.getCurrentOffer().isValid() || !this.game.getCurrentOffer().getReceiver().is(this)) {
            throw new InvalidTradeOfferException(this.game.getCurrentOffer());
        }
        this.game.setCurrentOffer(null);
        this.game.setCurrentPlayer(this.game.getCurrentOffer().getSender());
        this.game.setPhase(GamePhase.trade);
    }
    
    public void tradeWithPlayer(Player otherPlayer, List<Buyable> incomingBuyables, List<Buyable> outgoingBuyables, int netBid)
            throws TileNotSellableException, TileNotBuyableException {
        List<Buyable> buyablesFromThisToOther = new ArrayList<>();
        List<Buyable> buyablesFromOtherToThis = new ArrayList<>();
        
        // Check if each player owns the supposed buyables, and carry the buyables to middle lists that will be added to
        // each other's buyables in the end.
        for (Buyable b : outgoingBuyables) {
            if (!b.getOwner().is(this)) {
                throw new TileNotSellableException(b);
            }
            buyablesFromThisToOther.add(b);
        }
        for (Buyable b : incomingBuyables) {
            if (!b.getOwner().is(otherPlayer)) {
                throw new TileNotBuyableException(b);
            }
            buyablesFromOtherToThis.add(b);
        }
    
        // All buyables are properly owned by supposed players, committing the changes.
        for (Buyable b : buyablesFromOtherToThis) {
            b.setOwner(this);
        }
        for (Buyable b : buyablesFromThisToOther) {
            b.setOwner(otherPlayer);
        }
        this.pay(netBid, otherPlayer);
    }
    
    public void earn(int amount) {
        this.balance += amount;
    }

    public void cheatToGetBankrupt() throws TileOfTypeNotFoundException {
        this.position = this.game.findFirstTileOfType(TileType.INCOME_TAX);
        this.pay(10_000_000);
        this.endTurn();
    }
    
    public void endTurn() {
        this.game.passTurnToNextPlayer();
        this.game.setPhase(GamePhase.roll);
    }
    
    /**
     * Returns whether this <code>Player</code> is the same <code>Player</code> as <code>p</code>.
     *
     * @param p The other <code>Tile</code> instance to be compared against this <code>Player</code> instance
     * @return Whether this and <code>p</code> are the same <code>Player</code>
     */
    public boolean is(Player p) {
        return this.getGameId().equals(p.getGameId()) && Objects.equals(this.getUserId(), p.getUserId());
    }

    public boolean equals(Object obj) {
        if (obj instanceof Player p) {
            return this.balance == p.balance &&
                    this.doubleRollStreak == p.doubleRollStreak &&
                    this.getGameId().equals(p.getGameId()) &&
                    this.name.equals(p.name) &&
                    this.position == p.position &&
                    this.turnsInJailLeft == p.turnsInJailLeft;
        }
        return false;
    }

    public String toString() {
        return String.format("Player: gameId=%s, userId=%s, name=%s, position=%d, balance=%d, doubleRollStreak=%d, turnsInJailLeft=%d, publicTransportsOwned=%d, buyables=%s",
                this.getGameId(), this.userId, this.name, this.position, this.balance, this.doubleRollStreak,
                this.turnsInJailLeft, this.numberOfPublicTransportsOwned(), this.buyables());
    }
}
