package types.gameplay;

import types.auth.User;
import types.gameplay.exceptions.NotEnoughMoneyToBuyException;
import types.gameplay.exceptions.TileNotBuyableException;
import types.gameplay.exceptions.TileNotFoundException;

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
    public boolean isOnUnownedBuyable;
    
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
        this.isOnUnownedBuyable = false;
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
        this.isOnUnownedBuyable = false;
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
        this.isOnUnownedBuyable = false;
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
        this.isOnUnownedBuyable = false;
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
        this.isOnUnownedBuyable = false;
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

    public List<Buyable> getBuyables() {
        return buyables;
    }

    public void setBuyables(List<Buyable> buyables) {
        this.buyables = buyables;
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

    public void incrementDoubleRollStreak() {
        this.doubleRollStreak += 1;
    }

    public void resetDoubleRollStreak() {
        this.doubleRollStreak = 0;
    }

    public void decrementTurnsInJailLeft() {
        this.turnsInJailLeft -= 1;
    }

    public void sendToJail() {
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
    public void handleDice() {
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
    private void advancePawn(int steps) {
        for (int i=0; i<steps; i++) {
            this.position++;
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
        this.publicTransportsOwned += 1;
    }
    
    public void releasePublicTransport(PublicTransport publicTransport) {
        this.buyables.remove(publicTransport);
        this.publicTransportsOwned -= 1;
    }
    
    public void passByTile(Tile tile) {
        tile.handlePlayerPassBy(this);
    }
    
    public void stepOnTile(Tile tile) {
        tile.handlePlayerStepOn(this);
    }
    
    public void pay(int amount) {
        this.balance -= amount;
    }
    
    public void pay(int amount, Player player) {
        this.balance -= amount;
        player.balance += amount;
    }
    
    public void earn(int amount) {
        this.balance += amount;
    }

    public void cheatToGetBankrupt() throws TileNotFoundException {
        this.position = this.game.findIncomeTax();
        this.pay(10_000_000);
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

    public void notifyClient(Game game) {
        // TODO: Implement this with web sockets
    }
}
