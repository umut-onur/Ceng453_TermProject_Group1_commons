package types.websocket;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.lang.NonNull;
import types.usercommands.UserCommand;

/**
 * GameMessage is the top-level message transmitted from client-to-server in a websocket communication.
 * It must either have <code>helloMessage</code> or <code>userCommand</code> fields set. It cannot have both.
 */
public class GameMessage {
    private HelloMessage helloMessage;  // the hello message that client sends after joining a game
    private UserCommand userCommand;    // the user-command message that client sends after making a move in the game

    /**
     * Initializes the GameMessage with a HelloMessage.
     */
    public GameMessage(@NonNull HelloMessage helloMessage) {
        this.helloMessage = helloMessage;
    }

    /**
     * Initializes the GameMessage with a UserCommand.
     */
    public GameMessage(@NonNull UserCommand userCommand) {
        this.userCommand = userCommand;
    }

    @JsonGetter
    public HelloMessage getHelloMessage() {
        return helloMessage;
    }

    @JsonGetter
    public UserCommand getUserCommand() {
        return userCommand;
    }

    @JsonSetter
    public void setHelloMessage(HelloMessage helloMessage) {
        this.helloMessage = helloMessage;
    }

    @JsonSetter
    public void setUserCommand(UserCommand userCommand) {
        this.userCommand = userCommand;
    }

    /**
     * @return The type of this message based on its internal fields.
     */
    public GameMessageType getMessageType() {
        if ((this.helloMessage == null && this.userCommand == null) || (this.helloMessage != null && this.userCommand != null)) {
            return GameMessageType.Invalid;
        } else if (this.helloMessage != null) {
            return GameMessageType.Hello;
        }
        return GameMessageType.UserCommand;
    }
}
