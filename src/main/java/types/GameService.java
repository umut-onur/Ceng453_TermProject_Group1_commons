package types;

import types.exceptions.NotEnoughPlayersException;

import java.util.List;
import java.util.Map;

public interface GameService {
    String getId();

    List<Player> getPlayers();

    int getNumberOfPlayers();

    Player getCurrentPlayer();

    List<Tile> getBoard();

    Long getStartedAt();

    Long getFinishedAt();

    void addPlayer(Player player);

    void removePlayer(Player player);

    void start() throws NotEnoughPlayersException;


    GameStatus getStatus();

    Map<String, Integer> getResults();
}

