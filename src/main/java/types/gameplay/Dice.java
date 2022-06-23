package types.gameplay;

import com.fasterxml.jackson.annotation.*;

import java.util.Random;

public class Dice implements GameEntity {
    private Game game;
    private String gameId;
    private int dieOne;
    private int dieTwo;

    @JsonCreator
    public Dice(@JsonProperty("game") Game game, @JsonProperty("dieOne") Integer dieOne, @JsonProperty("dieTwo") Integer dieTwo) {
        this.game = game;
        this.gameId = game.getId();
        this.dieOne = dieOne;
        this.dieTwo = dieTwo;
    }

    public Dice(Game game) {
        this(game, 0, 0);
    }

    @JsonGetter
    @Override
    public String getGameId() {
        return this.gameId;
    }

    @JsonIgnore
    public void roll() {
        Random rand = new Random();
        this.dieOne = rand.nextInt(6) + 1;
        this.dieTwo = rand.nextInt(6) + 1;
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
    
    
    @JsonIgnore
    @Override
    public String toString() {
        return "D[" + this.dieOne + ", " + this.dieTwo + "]";
    }
}
