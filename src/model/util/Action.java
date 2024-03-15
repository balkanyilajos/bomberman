package model.util;

public class Action {
    public boolean left;
    public boolean right;
    public boolean up;
    public boolean down;
    
    public Action() {
        this(false, false, false, false);
    }

    public Action(boolean up, boolean down, boolean left, boolean right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }
    
    public boolean any() {
        return left || right || up || down;
    }

}
