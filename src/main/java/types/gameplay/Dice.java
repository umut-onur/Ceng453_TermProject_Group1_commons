package types.gameplay;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Random;

public class Dice {
    private Game game;
    private int dieOne;
    private int dieTwo;

    public Dice() {}

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

    @JsonGetter
    public Game getGame() {
        return game;
    }

    @JsonGetter
    public int getDieOne() {
        return dieOne;
    }

    @JsonGetter
    public int getDieTwo() {
        return dieTwo;
    }

    @JsonSetter
    public void setGame(Game game) {
        this.game = game;
    }

    @JsonSetter
    public void setDieOne(int dieOne) {
        this.dieOne = dieOne;
    }

    @JsonSetter
    public void setDieTwo(int dieTwo) {
        this.dieTwo = dieTwo;
    }

    @JsonIgnore
    public int getTotal() {
        return dieOne + dieTwo;
    }

    @JsonIgnore
    public boolean isDoubleDice() {
        return (dieOne != 0 && dieTwo != 0) && dieOne == dieTwo;
    }
}
