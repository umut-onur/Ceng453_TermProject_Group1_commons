package types.gameplay;

import java.util.HashMap;
import java.util.Map;

public class GameMaster {
    private static volatile GameMaster instance = new GameMaster();
    private final Map<String, Game> activeGames;
    
    private GameMaster() {
        this.activeGames = new HashMap<String, Game>();
    }
    
    public static GameMaster getInstance() {
        if (instance == null) {
            synchronized (GameMaster.class) {
                if (instance == null) {
                    instance = new GameMaster();
                }
            }
        }
        return instance;
    }
    
    public void addActiveGame(Game game) {
        this.activeGames.put(game.getId(), game);
    }
    
    public void removeActiveGame(Game game) {
        this.activeGames.remove(game.getId());
    }
    
    public void removeActiveGame(String gameId) {
        this.activeGames.remove(gameId);
    }
    
    public void clearActiveGames() {
        this.activeGames.clear();
    }
    
    public Game getActiveGame(String gameId) {
        return this.activeGames.get(gameId);
    }
    
    public Map<String, Game> getAllActiveGames() {
        return this.activeGames;
    }
    
    public void updateActiveGame(Game game) {
        this.activeGames.put(game.getId(), game);
        this.notifyClients(game);
    }
    
    @Override
    public String toString() {
        return this.activeGames.toString();
    }
    
    private void notifyClients(Game game) {
        for (Player p : game.getPlayers()) {
            p.notifyClient(game);
        }
    }
}
