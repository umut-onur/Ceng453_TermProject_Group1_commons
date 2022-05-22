package types.game;



import types.exceptions.NotEnoughPlayersException;

import java.util.*;

public class Game implements GameService {
    private String id;
    private List<Player> players;
    private Player currentPlayer;
    private List<Tile> board;
    private Long startedAt;
    private Long finishedAt;

    public Game() {
    }

    public Game(String gameId, List<Player> players) {
        this.id = gameId;
        this.players = players;
        this.board = new ArrayList<Tile>();
        this.currentPlayer = null;
        this.startedAt = null;
        this.finishedAt = null;
    }

    public Game(String gameId) {
        this.id = gameId;
        this.players = new ArrayList<Player>();
        this.board = new ArrayList<Tile>();
        this.currentPlayer = null;
        this.startedAt = null;
        this.finishedAt = null;
    }

    public Game(String gameId, Long startedAt, Long finishedAt) {
        this.id = gameId;
        this.players = new ArrayList<Player>();
        this.board = new ArrayList<Tile>();
        this.currentPlayer = null;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    @Override
    public String getId() {
        return id;
    }


    @Override
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public int getNumberOfPlayers() {
        return this.players.size();
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public List<Tile> getBoard() {
        return board;
    }

    public void setBoard(List<Tile> board) {
        this.board = board;
    }

    @Override
    public Long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Long startedAt) {
        this.startedAt = startedAt;
    }

    @Override
    public Long getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Long finishedAt) {
        this.finishedAt = finishedAt;
    }

    @Override
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    @Override
    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    private void initializeBoard() {
        // TODO: Modify this so that the random distribution becomes normal.
        List<Property> properties = new ArrayList<Property>();
        List<PublicTransport> publicTransports = new ArrayList<PublicTransport>();
        List<IncomeTax> incomeTaxes = new ArrayList<IncomeTax>();

        for (int i = 0; i < 8; i++) {
            properties.add(new Property(this.id, String.format("Property-%d", i), 100 + 50 * i));
        }
        for (int i = 0; i < 4; i++) {
            publicTransports.add(new PublicTransport(this.id, String.format("Transport-%d", i), 250));
        }
        incomeTaxes.add(new IncomeTax(this.id));

        while (!(properties.isEmpty() && publicTransports.isEmpty() && incomeTaxes.isEmpty())) {
            Random rand = new Random();
            int randInt = rand.nextInt(3);
            switch (randInt) {
                case 0:
                    if (!properties.isEmpty()) {
                        board.add(properties.remove(0));
                    }
                    break;
                case 1:
                    if (!publicTransports.isEmpty()) {
                        board.add(publicTransports.remove(0));
                    }
                    break;
                case 2:
                    if (!incomeTaxes.isEmpty()) {
                        board.add(incomeTaxes.remove(0));
                    }
                    break;
                default:
                    break;
            }
        }

        board.add(0, new StartingPoint(this.id));
        board.add(4, new GoToJail(this.id));
        board.add(12, new JustVisiting(this.id));

        for (int i = 0; i < board.size(); i++) {
            board.get(i).setPosition(i);
        }
    }

    @Override
    public void start() throws NotEnoughPlayersException {
        this.initializeBoard();
        this.currentPlayer = this.players.get(this.players.size() - 1);
        this.startedAt = System.currentTimeMillis() / 1000L;
        this.passTurnToNextPlayer(false);
    }

    void passTurnToNextPlayer(boolean currentPlayerWillRepeat) {
        if (this.shouldEnd()) {
            this.finish();
        } else {
            if (!currentPlayerWillRepeat) {
                this.currentPlayer = this.currentPlayer == this.players.get(this.players.size() - 1)
                        ? this.players.get(0)
                        : this.players.get(this.players.indexOf(this.currentPlayer) + 1);
            }
            this.currentPlayer.takeTurn();
        }

    }

    private boolean shouldEnd() {
        for (Player p : this.players) {
            if (p.getBalance() < 0) {
                return true;
            }
        }
        return false;
    }

    public void finish() {
        this.finishedAt = System.currentTimeMillis() / 1000L;
    }

    @Override
    public Map<String, Integer> getResults() {
        Map<String, Integer> gameResult = new HashMap<>();
        for (Player p : this.players) {
            gameResult.put(p.getName(), p.getScore());
        }
        return gameResult;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Game g) {
            return this.id.equals(g.id) &&
                    this.board.equals(g.board) &&
                    (this.currentPlayer == null ? g.currentPlayer == null : this.currentPlayer.equals(g.currentPlayer)) &&
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

