package types.game;


import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("PublicTransport")
public class PublicTransport implements Buyable {
    private String gameId;
    private String name;
    private int firstCost;
    private Player owner;
    private int position;

    public PublicTransport() {}

    public PublicTransport(String gameId, String name, int firstCost, int position) {
        this.gameId = gameId;
        this.name = name;
        this.firstCost = firstCost;
        this.owner = null;
        this.position = position;
    }

    public PublicTransport(String gameId, String name, int firstCost) {
        this.gameId = gameId;
        this.name = name;
        this.firstCost = firstCost;
        this.owner = null;
        this.position = -99;
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
    public int getFirstCost() {
        return firstCost;
    }

    @Override
    public int getRentForPlayer(Player player) {
        return (firstCost / 10) * player.getPublicTransportsOwned();
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Player owner) {
        this.owner = owner;
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

