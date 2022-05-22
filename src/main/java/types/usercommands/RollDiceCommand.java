package types.usercommands;

public class RollDiceCommand extends UserCommand {

    public RollDiceCommand() {
        super();
    }

    @Override
    public UserCommandType getCommandType() {
        return UserCommandType.ROLL_DICE;
    }
}
