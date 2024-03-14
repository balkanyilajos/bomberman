package model.util;

public class Action {
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    
    public Action() {
        this(false);
    }

    public Action(boolean init) {
        left = right = up = down = init;
    }
    
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    

}
