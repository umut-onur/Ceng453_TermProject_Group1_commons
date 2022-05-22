package types;


import types.exceptions.NotEnoughMoneyToBuyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player implements GameEntity {
    private String gameId;
    private String userId;
    private String name;
    private int balance;
    private List<Buyable> buyables;
    private int doubleRollStreak;
    private int turnsInJailLeft;
    private int publicTransportsOwned;
    private int position;

    public Player() {
    }

    public Player(String gameId, String userId, String name) {
        this.gameId = gameId;
        this.userId = userId;
        this.name = name;
        this.balance = 1500;
        this.buyables = new ArrayList<>();
        this.doubleRollStreak = 0;
        this.turnsInJailLeft = 0;
        this.publicTransportsOwned = 0;
        this.position = 0;
    }

    public Player(String gameId, String name) {
        this.gameId = gameId;
        this.userId = null;
        this.name = name;
        this.balance = 1500;
        this.buyables = new ArrayList<>();
        this.doubleRollStreak = 0;
        this.turnsInJailLeft = 0;
        this.publicTransportsOwned = 0;
        this.position = 0;
    }

    public Player(String gameId, User user) {
        this.gameId = gameId;
        this.userId = user.getId();
        this.name = user.getUsername();
        this.balance = 1500;
        this.buyables = new ArrayList<>();
        this.doubleRollStreak = 0;
        this.turnsInJailLeft = 0;
        this.publicTransportsOwned = 0;
        this.position = 0;
    }

    public Player(String gameId, User user, String name) {
        this.gameId = gameId;
        this.userId = user.getId();
        this.name = name;
        this.balance = 1500;
        this.buyables = new ArrayList<>();
        this.doubleRollStreak = 0;
        this.turnsInJailLeft = 0;
        this.publicTransportsOwned = 0;
        this.position = 0;
    }

    @Override
    public String getGameId() {
        return gameId;
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

    public void rollDiceAndAdvancePawn() {
        Random rand = new Random();
        int firstNumberRolled = rand.nextInt(6) + 1;
        int secondNumberRolled = rand.nextInt(6) + 1;
        if (firstNumberRolled == secondNumberRolled) {
            this.incrementDoubleRollStreak();
            if (this.doubleRollStreak == 3) {
                this.resetDoubleRollStreak();
                this.sendToJail();
            }
        } else {
            this.resetDoubleRollStreak();
            this.position += firstNumberRolled + secondNumberRolled;
        }
    }

    public void buyBuyable(Buyable buyable) throws NotEnoughMoneyToBuyException {
        if (this.balance < buyable.getFirstCost()) {
            throw new NotEnoughMoneyToBuyException(this, buyable);
        }
        this.balance -= buyable.getFirstCost();
        this.buyables.add(buyable);
        buyable.setOwner(this);
    }

    public void cheatToGetBankrupt() {
        // TODO
    }

    public void takeTurn() {
        // TODO
    }

    public boolean equals(Object obj) {
        if (obj instanceof Player p) {
            return this.balance == p.balance &&
                    (this.buyables == null ? p.buyables == null : this.buyables.equals(p.buyables)) &&
                    this.doubleRollStreak == p.doubleRollStreak &&
                    this.gameId.equals(p.gameId) &&
                    this.name.equals(p.name) &&
                    this.position == p.position &&
                    this.publicTransportsOwned == p.publicTransportsOwned &&
                    this.turnsInJailLeft == p.turnsInJailLeft;
        }
        return false;
    }

    public String toString() {
        return String.format("Player: gameId=%s, userId=%s, name=%s, position=%d, balance=%d, doubleRollStreak=%d, turnsInJailLeft=%d, publicTransportsOwned=%d, buyables=%s",
                this.gameId, this.userId, this.name, this.position, this.balance, this.doubleRollStreak,
                this.turnsInJailLeft, this.publicTransportsOwned, this.buyables);
    }

    public void notifyClient(Game game) {
        // TODO: Implement this with web sockets
    }
}
