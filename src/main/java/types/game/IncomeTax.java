package types.game;


import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("IncomeTax")
public class IncomeTax implements Tile {
    private String gameId;
    private String name;
    private int position;
    private int taxAmount;

    public IncomeTax() {}

    public IncomeTax(String gameId, int position) {
        this.gameId = gameId;
        this.name = "Income Tax";
        this.position = position;
        this.taxAmount = 50;
    }

    public IncomeTax(String gameId) {
        this.gameId = gameId;
        this.name = "Income Tax";
        this.position = -99;
        this.taxAmount = 50;
    }

    @Override
    public String getGameId() {
        return gameId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPosition() {
        return position;
    }

    public int getTaxAmount() {
        return taxAmount;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }
}

