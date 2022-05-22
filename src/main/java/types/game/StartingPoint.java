package types.game;


import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("StartingPoint")
public class StartingPoint implements Tile {
    private String gameId;
    private String name;
    private int position;
    private int passingReward;

    public StartingPoint() {}

    public StartingPoint(String gameId) {
        this.gameId = gameId;
        this.name = "Starting Point";
        this.position = 0;
        this.passingReward = 100;
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

    public int getPassingReward() {
        return passingReward;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }
}

