package model.util;
import java.util.Random;

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

    public void changeDirection()
    {
        boolean[] dirs = new boolean[] {up, right, down, left};
        int out = up ? 0 : (right ? 1 : (down ? 2 : 3) );
        Random rnd = new Random();
        int n = rnd.nextInt(dirs.length);
        if(n == out)
        {
            n = (n+1)%dirs.length;
        }
        this.up = 0 == n;
        this.right = 1 == n;
        this.down = 2 == n;
        this.left = 3 == n;
    }
    
    public boolean any() {
        return left || right || up || down;
    }

}
