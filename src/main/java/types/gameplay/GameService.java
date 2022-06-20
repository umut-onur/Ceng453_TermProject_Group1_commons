package types.gameplay;

import types.websocket.TradeOffer;

import java.util.List;
import java.util.Map;

public interface GameService {
    // Getters
    String getId();
    
    List<Player> getPlayers();
    
    int getNumberOfPlayers();
    
    Player getCurrentPlayer();
    
    Dice getDice();
    
    List<Tile> getBoard();
    
    TradeOffer getCurrentOffer();
    
    Long getStartedAt();
    
    Long getFinishedAt();
    
    GameStatus getStatus();
    
    Map<String, Integer> getResults();
    
    /**
     * Adds the given <code>Player</code> to the player list of the game.
     *
     * @param player The player to be added to the player list of the game.
     */
    void addPlayer(Player player);
    
    /**
     * Removes the given <code>Player</code> from the player list of the game.
     *
     * @param player The player to be removed from the player list of the game.
     */
    void removePlayer(Player player);
    
    /**
     * Adds this <code>Game</code> to the <code>activeGames</code> of <code>GameMaster</code>, and sets the
     * <code>startedAt</code> field to the current time.
     */
    void start();
}
