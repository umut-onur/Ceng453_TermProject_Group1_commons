package types.websocket;

/**
 * The type of the content of the game message. It should not be stored directly and should be computed dynamically.
 */
public enum GameMessageType {
    Invalid,
    GameState,
    Error
}
