package test;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.*;

import model.GameModel;
import model.sprite.Sprite;

public class BombTest {
    private Thread modelThread;
    private GameModel model;

    public BombTest() {
        model = new GameModel(null, "test/bombTestMap/bombBox.txt", 1, new ArrayList<>());
        model.start(new Dimension(50, 50));
    }

    @Test
    public void bombBox() {
        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        System.out.println(original.size());
        System.out.println(afterBomb);
        Assert.assertEquals(afterBomb.size(), original.size()-1);
    }
}
