package types.gameplay;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("IncomeTax")
public class IncomeTax implements Tile {
    private Game game;
    private String name;
    private int position;
    private int taxAmount;
    
    public IncomeTax() {}
    
    public IncomeTax(Game game, int position) {
        this.game = game;
        this.name = "Income Tax";
        this.position = position;
        this.taxAmount = 50;
    }
    
    public IncomeTax(Game game) {
        this.game = game;
        this.name = "Income Tax";
        this.position = -99;
        this.taxAmount = 50;
    }
    
    @Override
    public String getGameId() {
        return game.getId();
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public int getPosition() {
        return position;
    }
    
    public int getTaxAmount() {
        return taxAmount;
    }
    
    @Override
    public void setPosition(int position) {
        this.position = position;
    }
    
    @Override
    public void handlePlayerPassBy(Player player) {}
    
    @Override
    public void handlePlayerStepOn(Player player) {
        player.pay(this.taxAmount);
    }
}
