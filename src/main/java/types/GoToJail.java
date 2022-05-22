package types;


import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("GoToJail")
public class GoToJail implements Tile {
    private String gameId;
    private String name;
    private int position;

    public GoToJail() {}

    public GoToJail(String gameId) {
        this.gameId = gameId;
        this.name = "Go to Jail";
        this.position = 4;
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

    @Override
    public void setPosition(int position) {
        this.position = position;
    }
}

