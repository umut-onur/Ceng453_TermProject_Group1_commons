package types.gameplay;

import com.fasterxml.jackson.annotation.JsonIgnore;
import types.auth.User;
import types.gameplay.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player implements GameEntity {
    private Game game;
    private String userId;
    private String name;
    private int balance;
    private List<Buyable> buyables;
    private int doubleRollStreak;
    private int turnsInJailLeft;
    private int publicTransportsOwned;
    private int position;
    private Dice mostRecentDice;
    
    public Player() {
        this.game = null;
        this.userId = null;
        this.name = null;
        this.balance = 1500;
        this.buyables = new ArrayList<>();
        this.doubleRollStreak = 0;
        this.turnsInJailLeft = 0;
        this.publicTransportsOwned = 0;
        this.position = 0;
        this.mostRecentDice = null;
    }

    public Player(Game game, String userId, String name) {
        this.game = game;
        this.userId = userId;
        this.name = name;
        this.balance = 1500;
        this.buyables = new ArrayList<>();
        this.doubleRollStreak = 0;
        this.turnsInJailLeft = 0;
        this.publicTransportsOwned = 0;
        this.position = 0;
        this.mostRecentDice = this.game.getDice();
    }

    public Player(Game game, String name) {
        this.game = game;
        this.userId = null;
        this.name = name;
        this.balance = 1500;
        this.buyables = new ArrayList<>();
        this.doubleRollStreak = 0;
        this.turnsInJailLeft = 0;
        this.publicTransportsOwned = 0;
        this.position = 0;
        this.mostRecentDice = this.game.getDice();
    }

    public Player(Game game, User user) {
        this.game = game;
        this.userId = user.getId();
        this.name = user.getUsername();
        this.balance = 1500;
        this.buyables = new ArrayList<>();
        this.doubleRollStreak = 0;
        this.turnsInJailLeft = 0;
        this.publicTransportsOwned = 0;
        this.position = 0;
        this.mostRecentDice = this.game.getDice();
    }

    public Player(Game game, User user, String name) {
        this.game = game;
        this.userId = user.getId();
        this.name = name;
        this.balance = 1500;
        this.buyables = new ArrayList<>();
        this.doubleRollStreak = 0;
        this.turnsInJailLeft = 0;
        this.publicTransportsOwned = 0;
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
    
    public boolean isOnUnownedBuyable() {
        return this.game.getBoard().get(this.getPosition()).canBeBought();
    }
    
    public void setBuyables(List<Buyable> buyables) {
        this.buyables = buyables;
    }
    
    public boolean ownsBuyable(Buyable buyable) {
        return buyable.getOwner().is(this);
    }

    public int getPublicTransportsOwned() {
        return publicTransportsOwned;
    }

    public int getPosition() {
        return position;
    }

    public int getScore() {
        int score = this.balance;
        for (Buyable b : this.buyables) {
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

    public void incrementPublicTransportsOwned() {
        this.publicTransportsOwned += 1;
    }

    public void decrementPublicTransportsOwned() {
        this.publicTransportsOwned -= 1;
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

    public void acquireProperty(Property property) {
        this.buyables.add(property);
    }
    
    public void releaseProperty(Property property) {
        this.buyables.remove(property);
    }
    
    public void acquirePublicTransport(PublicTransport publicTransport) {
        this.buyables.add(publicTransport);
        this.incrementPublicTransportsOwned();
    }
    
    public void releasePublicTransport(PublicTransport publicTransport) {
        this.buyables.remove(publicTransport);
        this.decrementPublicTransportsOwned();
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
    
    public void tradeWithPlayer(Player otherPlayer, List<Buyable> incomingBuyables, List<Buyable> outgoingBuyables, int netBid)
            throws TileNotSellableException, TileNotBuyableException {
        List<Buyable> buyablesFromThisToOther = new ArrayList<>();
        List<Buyable> buyablesFromOtherToThis = new ArrayList<>();
        
        // Check if each player owns the supposed buyables, and carry the buyables to middle lists that will be added to
        // each other's buyables in the end.
        try {
            for (Buyable b : outgoingBuyables) {
                if (!b.getOwner().is(this)) {
                    throw new TileNotSellableException(b);
                }
                buyablesFromThisToOther.add(b);
                this.buyables.remove(b);
            }
            for (Buyable b : incomingBuyables) {
                if (!b.getOwner().is(otherPlayer)) {
                    throw new TileNotBuyableException(b);
                }
                buyablesFromOtherToThis.add(b);
                otherPlayer.buyables.remove(b);
            }
            
            // All buyables are properly owned by supposed players, committing the changes.
            // TODO: Change the owner of each buyable
            this.buyables.addAll(buyablesFromOtherToThis);
            otherPlayer.buyables.addAll(buyablesFromThisToOther);
            this.pay(netBid, otherPlayer);
            
        // Some buyable is not owned by its supposed player (or any other exception that prevents the transaction from
        // being carried out), reverting the changes and re-throwing the exception.
        } catch (TileTradeException e) {
            this.buyables.addAll(buyablesFromThisToOther);
            otherPlayer.buyables.addAll(buyablesFromOtherToThis);
            throw e;
        }
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
    
    @Override
    public boolean is(Object obj) {
        if (obj instanceof Player p) {
            return this.getGameId().equals(p.getGameId()) && Objects.equals(this.getUserId(), p.getUserId());
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Player p) {
            return this.balance == p.balance &&
                    (this.buyables == null ? p.buyables == null : this.buyables.equals(p.buyables)) &&
                    this.doubleRollStreak == p.doubleRollStreak &&
                    this.getGameId().equals(p.getGameId()) &&
                    this.name.equals(p.name) &&
                    this.position == p.position &&
                    this.publicTransportsOwned == p.publicTransportsOwned &&
                    this.turnsInJailLeft == p.turnsInJailLeft;
        }
        return false;
    }

    public String toString() {
        return String.format("Player: gameId=%s, userId=%s, name=%s, position=%d, balance=%d, doubleRollStreak=%d, turnsInJailLeft=%d, publicTransportsOwned=%d, buyables=%s",
                this.getGameId(), this.userId, this.name, this.position, this.balance, this.doubleRollStreak,
                this.turnsInJailLeft, this.publicTransportsOwned, this.buyables);
    }
}
