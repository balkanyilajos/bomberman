package model.util;

public class PlayerAction extends Action {
    
    public boolean placeBomb;
    public boolean placeBarrier;

    public PlayerAction() {
        super();
        placeBomb = placeBarrier = false;
    }
    
}
