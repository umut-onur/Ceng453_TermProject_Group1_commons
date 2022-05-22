package types.game;


import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("JustVisiting")
public class JustVisiting implements Tile {
    private String gameId;
    private String name;
    private int position;

    public JustVisiting() {}

    public JustVisiting(String gameId) {
        this.gameId = gameId;
        this.name = "Jail/Just Visiting";
        this.position = 12;
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

