package types.gameplay.exceptions;

import types.gameplay.Buyable;
import types.gameplay.Player;

public class NotEnoughMoneyToBuyException extends GameplayException {
    public NotEnoughMoneyToBuyException(Player player, Buyable buyable) {
        super(
                "Player " + player.getName() + " with balance " + player.getBalance() + " cannot afford to buy " + buyable.getName() + " with price " + buyable.getFirstCost() + "."
        );
    }
}
