package types.gameplay;

import java.util.Random;

public class Dice {
    private Game game;
    private int dieOne;
    private int dieTwo;
    
    public Dice(Game game) {
        this.game = game;
        this.dieOne = 0;
        this.dieTwo = 0;
    }
    
    public String getGameId() {
        return game.getId();
    }
    
    public void roll() {
        Random rand = new Random();
        this.dieOne = rand.nextInt(6) + 1;
        this.dieTwo = rand.nextInt(6) + 1;
    }
    
    public int getDieOne() {
        return dieOne;
    }
    
    public int getDieTwo() {
        return dieTwo;
    }
    
    public int getTotal() {
        return dieOne + dieTwo;
    }
    
    public boolean isDoubleDice() {
        return (dieOne != 0 && dieTwo != 0) && dieOne == dieTwo;
    }
}
