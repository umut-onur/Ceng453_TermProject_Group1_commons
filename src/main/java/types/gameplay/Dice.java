package types.gameplay;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

public class Dice {
    private Game game;
    private int dieOne;
    private int dieTwo;

    @JsonCreator
    public Dice(@JsonProperty("game") Game game, @JsonProperty("dieOne") Integer dieOne, @JsonProperty("dieTwo") Integer dieTwo) {
        this.game = game;
        this.dieOne = dieOne;
        this.dieTwo = dieTwo;
    }

    public Dice(Game game) {
        this(game, 0, 0);
    }

    public String getGameId() {
        return game.getId();
    }

    public void roll() {
        Random rand = new Random();
        //this.dieOne = rand.nextInt(6) + 1;
        //this.dieTwo = rand.nextInt(6) + 1;
        this.dieOne = 2;
        this.dieTwo = 2;
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