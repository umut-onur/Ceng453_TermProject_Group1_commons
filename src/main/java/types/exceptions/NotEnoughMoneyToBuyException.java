package types.exceptions;

import types.game.Buyable;
import types.game.Player;

public class NotEnoughMoneyToBuyException extends GameplayException {
    public NotEnoughMoneyToBuyException(Player player, Buyable buyable) {
        super(
                "Player " + player.getName() + " with balance " + player.getBalance() + " cannot afford to buy " + buyable.getName() + " with price " + buyable.getFirstCost() + "."
        );
    }
}
