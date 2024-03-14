package model.util;

public class PlayerAction extends Action {
    
    private boolean placeBomb;
    private boolean placeBarrier;

    public PlayerAction() {
        super();
        placeBomb = placeBarrier = false;
    }


    public boolean isPlaceBomb() {
        return placeBomb;
    }

    public void setPlaceBomb(boolean placeBomb) {
        this.placeBomb = placeBomb;
    }

    public boolean isPlaceBarrier() {
        return placeBarrier;
    }

    public void setPlaceBarrier(boolean placeBarrier) {
        this.placeBarrier = placeBarrier;
    }    

}
