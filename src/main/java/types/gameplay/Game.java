package types.gameplay;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import types.gameplay.exceptions.TileOfTypeNotFoundException;

import java.util.*;

public class Game implements GameService {
    private String id;
    private String hostUserId;
    private List<Player> players;
    private Player currentPlayer;
    private GamePhase phase;
    private final Dice dice;
    private TradeOffer currentOffer;
    private List<Tile> board;
    private Long startedAt;
    private Long finishedAt;

    public Game() {
        this.id = null;
        this.hostUserId = null;
        this.players = new ArrayList<Player>();
        this.board = new ArrayList<Tile>();
        this.currentPlayer = null;
        this.phase = null;
        this.dice = new Dice(this);
        this.currentOffer = null;
        this.startedAt = null;
        this.finishedAt = null;
    }

    public Game(String gameId, String hostUserId) {
        this.id = gameId;
        this.hostUserId = hostUserId;
        this.players = new ArrayList<Player>();
        this.board = new ArrayList<Tile>();
        this.currentPlayer = null;
        this.phase = null;
        this.dice = new Dice(this);
        this.currentOffer = null;
        this.startedAt = null;
        this.finishedAt = null;
    }

    public Game(String gameId, String hostUserId, Long startedAt, Long finishedAt) {
        this.id = gameId;
        this.hostUserId = hostUserId;
        this.players = new ArrayList<Player>();
        this.board = new ArrayList<Tile>();
        this.currentPlayer = null;
        this.phase = null;
        this.dice = new Dice(this);
        this.currentOffer = null;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    // Getters
    @JsonGetter
    @Override
    public String getId() {
        return id;
    }

    @JsonGetter
    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @JsonIgnore
    @Override
    public int getNumberOfPlayers() {
        return this.players.size();
    }
    
    @JsonGetter
    @Override
    public String getHostUserId() {
        return this.hostUserId;
    }
    
    @JsonGetter
    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @JsonGetter
    @Override
    public List<Tile> getBoard() {
        return board;
    }

    @JsonGetter
    @Override
    public Dice getDice() {
        return dice;
    }

    @JsonGetter
    @Override
    public TradeOffer getCurrentOffer() {
        return this.currentOffer;
    }

    @JsonGetter
    public GamePhase getPhase() {
        return phase;
    }

    @JsonGetter
    @Override
    public Long getStartedAt() {
        return startedAt;
    }

    @JsonGetter
    @Override
    public Long getFinishedAt() {
        return finishedAt;
    }

    @JsonIgnore
    @Override
    public GameStatus getStatus() {
        if (this.startedAt == null) {
            return GameStatus.hasNotStartedYet;
        }
        if (this.finishedAt == null) {
            return GameStatus.goingOn;
        }
        return GameStatus.alreadyFinished;
    }

    @JsonIgnore
    @Override
    public Map<String, Integer> getResults() {
        Map<String, Integer> gameResult = new HashMap<>();
        for (Player p : this.players) {
            gameResult.put(p.getName(), p.getScore());
        }
        return gameResult;
    }
    
    // Setters
    @JsonSetter
    public void setId(String id) {
        this.id = id;
    }

    @JsonSetter
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @JsonSetter
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    
    @JsonSetter
    public void setHostUserId(String hostUserId) {
        this.hostUserId = hostUserId;
    }

    @JsonSetter
    public void setPhase(GamePhase phase) {
        this.phase = phase;
    }

    @JsonSetter
    public void setBoard(List<Tile> board) {
        this.board = board;
    }

    @JsonSetter
    public void setCurrentOffer(TradeOffer currentOffer) {
        this.currentOffer = currentOffer;
    }

    @JsonSetter
    public void setStartedAt(Long startedAt) {
        this.startedAt = startedAt;
    }

    @JsonSetter
    public void setFinishedAt(Long finishedAt) {
        this.finishedAt = finishedAt;
    }

    @JsonIgnore
    @Override
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    @JsonIgnore
    @Override
    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    @JsonIgnore
    public Player playerOfUser(String userId) {
        for (Player p : this.players) {
            if (Objects.equals(p.getUserId(), userId)) {
                return p;
            }
        }
        return null;
    }

    @JsonIgnore
    private void initializeBoard() {
        List<Property> properties = new ArrayList<Property>();
        List<PublicTransport> publicTransports = new ArrayList<PublicTransport>();
        Random rand = new Random();

        for (int i = 0; i < 8; i++) {
            properties.add(new Property(this, String.format("Property-%d", i), 100 + 50 * i));
        }
        for (int i = 0; i < 4; i++) {
            publicTransports.add(new PublicTransport(this, String.format("Transport-%d", i), 250));
        }
        
        board.addAll(properties);
        for (int i=0; i<4; i++) {
            board.add(rand.nextInt(board.size()), publicTransports.get(i));
        }
        board.add(rand.nextInt(board.size()), new IncomeTax(this));
        board.add(0, new StartingPoint(this));
        board.add(4, new GoToJail(this));
        board.add(12, new JustVisiting(this));

        for (int i = 0; i < board.size(); i++) {
            board.get(i).setPosition(i);
        }
    }

    @JsonIgnore
    @Override
    public void start() {
        this.initializeBoard();
        this.currentPlayer = this.playerOfUser(this.hostUserId);
        this.phase = GamePhase.roll;
        this.startedAt = System.currentTimeMillis() / 1000L;
    }

    @JsonIgnore
    void passTurnToNextPlayer() {
        if (!currentPlayer.willRepeatTurn()) {
            this.currentPlayer = (this.currentPlayer == this.players.get(this.players.size() - 1))
                    ? this.players.get(0)
                    : this.players.get(this.players.indexOf(this.currentPlayer) + 1);
        }
        if (this.currentPlayer.getTurnsInJailLeft() > 0) {
            this.currentPlayer.decrementTurnsInJailLeft();
            this.passTurnToNextPlayer();
        }
    }

    @JsonIgnore
    public int findFirstTileOfType(TileType type) throws TileOfTypeNotFoundException {
        for (Tile t : this.board) {
            if (t.getType() == type) {
                return t.getPosition();
            }
        }
        throw new TileOfTypeNotFoundException(type);
    }

    @JsonIgnore
    public boolean shouldEnd() {
        for (Player p : this.players) {
            if (p.getBalance() < 0) {
                return true;
            }
        }
        return false;
    }

    @JsonIgnore
    public void finish() {
        this.finishedAt = System.currentTimeMillis() / 1000L;
    }

    @JsonIgnore
    public boolean is(Object obj) {
        if (obj instanceof Game g) {
            return Objects.equals(this.id, g.id);
        }
        return false;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Game g) {
            return Objects.equals(this.id, g.id) &&
                    this.board.equals(g.board) &&
                    (this.currentPlayer == null ? g.currentPlayer == null : this.currentPlayer.is(g.currentPlayer)) &&
                    (this.finishedAt == null ? g.finishedAt == null : this.finishedAt.equals(g.finishedAt)) &&
                    (this.startedAt == null ? g.startedAt == null : this.startedAt.equals(g.startedAt));
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Game: id=%s, startedAt=%s, finishedAt=%s, currentPlayer=%s, players=%s, board=%s",
                this.id, this.startedAt, this.finishedAt, this.currentPlayer, this.players, this.board);
    }
}
