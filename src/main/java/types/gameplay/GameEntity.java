package types.gameplay;

public interface GameEntity {
    /**
     * @return The ID of the <Code>Game</Code> of this <code>GameEntity</code>
     */
    String getGameId();
    
    /**
     * Returns whether this <code>GameEntity</code> refers to the same object as <code>obj</code>.
     *
     * @param obj The other <code>Object</code> to be compared against this <code>GameEntity</code>
     * @return Whether this <code>Object</code> and <code>obj</code> refer to the same <code>Object</code>
     */
    boolean is(Object obj);
}
