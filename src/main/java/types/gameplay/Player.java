package types.gameplay;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
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

    @JsonIgnore
    @Override
    public String getGameId() {
        return game.getId();
    }

    @JsonGetter
    public String getUserId() {
        return userId;
    }

    @JsonGetter
    public String getName() {
        return name;
    }

    @JsonGetter
    public int getBalance() {
        return balance;
    }

    @JsonGetter
    public int getDoubleRollStreak() {
        return doubleRollStreak;
    }

    @JsonGetter
    public int getTurnsInJailLeft() {
        return turnsInJailLeft;
    }

    @JsonGetter
    public int getPosition() {
        return position;
    }

    @JsonGetter
    public Dice getMostRecentDice() {
        return mostRecentDice;
    }

    @JsonIgnore
    public boolean isOnUnownedBuyable() {
        return this.game.getBoard().get(this.getPosition()).canBeBought();
    }

    @JsonIgnore
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

    @JsonIgnore
    public int numberOfPublicTransportsOwned() {
        int numberOfPublicTransportsOwned = 0;
        for (Buyable b : this.buyables()) {
            if (b.getType().equals(TileType.PUBLIC_TRANSPORT)) {
                numberOfPublicTransportsOwned++;
            }
        }
        return numberOfPublicTransportsOwned;
    }

    @JsonIgnore
    public boolean ownsBuyable(Buyable buyable) {
        return buyable.getOwner() != null && this.is(buyable.getOwner());
    }

    @JsonIgnore
    public int getScore() {
        int score = this.balance;
        for (Buyable b : this.buyables()) {
            score += b.getFirstCost();
        }
        return score;
    }

    @JsonIgnore
    public void setGame(Game game) {
        this.game = game;
    }

    @JsonSetter
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonSetter
    public void setName(String name) {
        this.name = name;
    }

    @JsonSetter
    public void setBalance(int balance) {
        this.balance = balance;
    }

    @JsonSetter
    public void setDoubleRollStreak(int doubleRollStreak) {
        this.doubleRollStreak = doubleRollStreak;
    }

    @JsonSetter
    public void setTurnsInJailLeft(int turnsInJailLeft) {
        this.turnsInJailLeft = turnsInJailLeft;
    }

    @JsonSetter
    public void setPosition(int position) {
        this.position = position;
    }

    @JsonSetter
    public void setMostRecentDice(Dice mostRecentDice) {
        this.mostRecentDice = mostRecentDice;
    }

    @JsonIgnore
    public void incrementDoubleRollStreak() {
        this.doubleRollStreak += 1;
    }

    @JsonIgnore
    public void resetDoubleRollStreak() {
        this.doubleRollStreak = 0;
    }

    @JsonIgnore
    public void decrementTurnsInJailLeft() {
        this.turnsInJailLeft -= 1;
    }

    @JsonIgnore
    public void sendToJail() throws TileOfTypeNotFoundException {
        this.position = this.game.findFirstTileOfType(TileType.JUST_VISITING);
        this.turnsInJailLeft = 2;
    }

    @JsonIgnore
    public boolean willRepeatTurn() {
        return this.mostRecentDice.isDoubleDice();
    }

    @JsonIgnore
    public void rollDice() {
        this.game.getDice().roll();
        this.mostRecentDice = this.game.getDice();
    }

    @JsonIgnore
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

    @JsonIgnore
    private void advancePawn(int steps) throws TileOfTypeNotFoundException {
        for (int i=0; i<steps; i++) {
            this.position = ((this.position + 1) % 16);
            this.passByTile(this.game.getBoard().get(this.position));
        }
        this.stepOnTile(this.game.getBoard().get(this.position));
        this.game.setPhase(GamePhase.trade);
    }

    @JsonIgnore
    public void buy() throws NotEnoughMoneyToBuyException, TileNotBuyableException {
        Tile tile = this.game.getBoard().get(this.position);
        tile.handlePlayerBuy(this);
    }

    @JsonIgnore
    public void passByTile(Tile tile) {
        tile.handlePlayerPassBy(this);
    }

    @JsonIgnore
    public void stepOnTile(Tile tile) throws TileOfTypeNotFoundException {
        tile.handlePlayerStepOn(this);
    }

    @JsonIgnore
    public void pay(int amount) {
        this.balance -= amount;
    }

    @JsonIgnore
    public void pay(int amount, Player player) {
        this.balance -= amount;
        player.balance += amount;
    }

    @JsonIgnore
    public void offerTrade(TradeOffer offer) throws InvalidTradeOfferException {
        if (!offer.isValid()) {
            throw new InvalidTradeOfferException(offer);
        }
        this.game.setCurrentOffer(offer);
        this.game.setCurrentPlayer(offer.getReceiver());
        this.game.setPhase(GamePhase.reply);
    }

    @JsonIgnore
    public void offerTrade(Player otherPlayer, List<Buyable> incomingBuyables, List<Buyable> outgoingBuyables, int netBid) throws InvalidTradeOfferException {
        TradeOffer offer = new TradeOffer(this, otherPlayer,incomingBuyables, outgoingBuyables, netBid);
        this.offerTrade(offer);
    }

    @JsonIgnore
    public void acceptTradeOffer() throws TileNotSellableException, TileNotBuyableException, InvalidTradeOfferException {
        if (!this.game.getCurrentOffer().isValid() || !this.game.getCurrentOffer().getReceiver().is(this)) {
            throw new InvalidTradeOfferException(this.game.getCurrentOffer());
        }
        this.tradeWithPlayer(this.game.getCurrentOffer().getReceiver(), this.game.getCurrentOffer().getBuyablesIn(), this.game.getCurrentOffer().getBuyablesOut(), this.game.getCurrentOffer().getNetBid());
        this.game.setCurrentOffer(null);
        this.game.setCurrentPlayer(this.game.getCurrentOffer().getSender());
        this.game.setPhase(GamePhase.trade);
    }

    @JsonIgnore
    public void rejectTradeOffer() throws InvalidTradeOfferException {
        if (!this.game.getCurrentOffer().isValid() || !this.game.getCurrentOffer().getReceiver().is(this)) {
            throw new InvalidTradeOfferException(this.game.getCurrentOffer());
        }
        this.game.setCurrentOffer(null);
        this.game.setCurrentPlayer(this.game.getCurrentOffer().getSender());
        this.game.setPhase(GamePhase.trade);
    }

    @JsonIgnore
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

    @JsonIgnore
    public void earn(int amount) {
        this.balance += amount;
    }

    @JsonIgnore
    public void cheatToGetBankrupt() throws TileOfTypeNotFoundException {
        this.position = this.game.findFirstTileOfType(TileType.INCOME_TAX);
        this.pay(10_000_000);
        this.endTurn();
    }

    @JsonIgnore
    public void endTurn() {
        this.game.passTurnToNextPlayer();
        this.game.setPhase(GamePhase.roll);
    }
    
    @JsonIgnore
    public void sellAll() throws TileNotSellableException {
        for (Buyable b : this.buyables()) {
            b.handlePlayerSell(this);
        }
    }
    
    @JsonIgnore
    public void giveAll() throws TileNotSellableException {
        this.sellAll();
        this.pay(this.getBalance());
    }
    
    @JsonIgnore
    public void abandonGame() throws TileNotSellableException {
        this.giveAll();
    }
    
    /**
     * Returns whether this <code>Player</code> is the same <code>Player</code> as <code>p</code>.
     *
     * @param p The other <code>Tile</code> instance to be compared against this <code>Player</code> instance
     * @return Whether this and <code>p</code> are the same <code>Player</code>
     */
    @JsonIgnore
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
