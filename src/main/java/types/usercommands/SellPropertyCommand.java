package types.usercommands;

public class SellPropertyCommand extends UserCommand {

    public SellPropertyCommand() {
        super();
    }

    @Override
    public UserCommandType getCommandType() {
        return UserCommandType.SELL_PROPERTY;
    }
}