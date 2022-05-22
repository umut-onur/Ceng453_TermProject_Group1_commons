package types.usercommands;


public class BuyPropertyCommand extends UserCommand {

    public BuyPropertyCommand() {
        super();
    }

    @Override
    public UserCommandType getCommandType() {
        return UserCommandType.BUY_PROPERTY;
    }
}
