package types.gameplay;

import java.util.List;
import java.util.Map;

public interface GameService {
    String getId();
    
    List<Player> getPlayers();
    
    int getNumberOfPlayers();
    
    Player getCurrentPlayer();
    
    Dice getDice();
    
    List<Tile> getBoard();
    
    Long getStartedAt();
    
    Long getFinishedAt();
    
    void addPlayer(Player player);
    
    void removePlayer(Player player);
    
    void start();
    
    
    GameStatus getStatus();
    
    Map<String, Integer> getResults();
}
