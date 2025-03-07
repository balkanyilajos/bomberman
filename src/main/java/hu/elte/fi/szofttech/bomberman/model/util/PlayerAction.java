package hu.elte.fi.szofttech.bomberman.model.util;

public class PlayerAction extends Action {

    public boolean placeBomb;
    public boolean placeBarrier;

    public PlayerAction() {
        super();
        placeBomb = placeBarrier = false;
    }

    public PlayerAction(boolean up, boolean down, boolean left, boolean right) {
        super(up, down, left, right);
        placeBomb = placeBarrier = false;
    }

    public PlayerAction(boolean up, boolean down, boolean left, boolean right, boolean placeBomb,
            boolean placeBarrier) {
        super(up, down, left, right);
        this.placeBomb = placeBomb;
        this.placeBarrier = placeBarrier;
    }

}
