package hu.elte.fi.szofttech.bomberman.model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.*;
import org.junit.jupiter.api.Test;

import hu.elte.fi.szofttech.bomberman.model.sprite.*;
import hu.elte.fi.szofttech.bomberman.model.sprite.fixedelement.*;
import hu.elte.fi.szofttech.bomberman.model.sprite.moveable.enemy.Balloon;
import hu.elte.fi.szofttech.bomberman.model.sprite.moveable.player.Player;
import hu.elte.fi.szofttech.bomberman.model.sprite.powerup.*;
import hu.elte.fi.szofttech.bomberman.model.sprite.weapon.Bomb;
import hu.elte.fi.szofttech.bomberman.GeneralPath;

public class BombTest {
    private final GameModel model;
    private final Dimension cubeSize;
    private String map;

    public BombTest() {
        map = "src/test/java/hu/elte/fi/szofttech/bomberman/model/standard-map.txt";
        model = new GameModel(null, map, 1, new ArrayList<>());
        cubeSize = new Dimension(50, 50);
    }

    @Test
    public void bombRight() {
        model.init(map, 1, 1);
        model.start((Dimension) cubeSize.clone());

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height), 1.0 * cubeSize.width, 0)
        );

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(1 * cubeSize.width, 0 * cubeSize.height), 1.0 * cubeSize.width)
        );

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 2);
        model.gameStop(true);
    }

    @Test
    public void bombLeft() {
        model.init(map, 1, 1);
        model.start((Dimension) cubeSize.clone());

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(1 * cubeSize.width, 0 * cubeSize.height), 1.0 * cubeSize.width, 0)
        );

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height), 1.0 * cubeSize.width)
        );

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 2);
        model.gameStop(true);
    }

    @Test
    public void bombUp() {
        model.init(map, 1, 1);
        model.start((Dimension) cubeSize.clone());

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(0 * cubeSize.width, 1 * cubeSize.height), 1.0 * cubeSize.width, 0)
        );

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height), 1.0 * cubeSize.width)
        );

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 2);
        model.gameStop(true);
    }

    @Test
    public void bombDown() {
        model.init(map, 1, 1);
        model.start((Dimension) cubeSize.clone());

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height), 1.0 * cubeSize.width, 0)
        );

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(0 * cubeSize.width, 1 * cubeSize.height), 1.0 * cubeSize.width)
        );

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 2);
        model.gameStop(true);
    }

    @Test
    public void bombUpRight() {
        model.init(map, 1, 1);
        model.start((Dimension) cubeSize.clone());

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(0 * cubeSize.width, 1 * cubeSize.height), 1.0 * cubeSize.width, 0)
        );

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(1 * cubeSize.width, 0 * cubeSize.height), 1.0 * cubeSize.width)
        );

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 1);
        model.gameStop(true);
    }

    @Test
    public void bombUpLeft() {
        model.init(map, 1, 1);
        model.start((Dimension) cubeSize.clone());

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(1 * cubeSize.width, 1 * cubeSize.height), 1.0 * cubeSize.width, 0)
        );

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height), 1.0 * cubeSize.width)
        );

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 1);
        model.gameStop(true);
    }

    @Test
    public void bombDownRight() {
        model.init(map, 1, 1);
        model.start((Dimension) cubeSize.clone());

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height), 1.0 * cubeSize.width, 0)
        );

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(1 * cubeSize.width, 1 * cubeSize.height), 1.0 * cubeSize.width)
        );

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 1);
        model.gameStop(true);
    }

    @Test
    public void bombDownLeft() {
        model.init(map, 1, 1);
        model.start((Dimension) cubeSize.clone());

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(1 * cubeSize.width, 0 * cubeSize.height), 1.0 * cubeSize.width, 0)
        );

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(0 * cubeSize.width, 1 * cubeSize.height), 1.0 * cubeSize.width)
        );

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 1);
        model.gameStop(true);
    }

    @Test
    public void bombBox() {
        model.start((Dimension) cubeSize.clone());
        model.addSpriteToBoard(new Box(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height)));
        model.addSpriteToBoard(new Box(model, new Point2D.Double(1 * cubeSize.width, 0 * cubeSize.height)));
        model.addSpriteToBoard(
                new Bomb(model, new Point2D.Double(2 * cubeSize.width, 0 * cubeSize.height), 2.0 * cubeSize.width, 0));

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        afterBomb.removeIf(sprite -> sprite instanceof PowerUp);

        Assert.assertEquals(afterBomb.size(), original.size() - 2);
        model.gameStop(true);
    }

    @Test
    public void bombBarrier() {
        model.init(map, 0, 1);
        model.start((Dimension) cubeSize.clone());
        model.addSpriteToBoard(new Barrier(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height)));
        model.addSpriteToBoard(new Barrier(model, new Point2D.Double(1 * cubeSize.width, 0 * cubeSize.height)));
        model.addSpriteToBoard(
                new Bomb(model, new Point2D.Double(2 * cubeSize.width, 0 * cubeSize.height), 2.0 * cubeSize.width, 0));

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 2);
        model.gameStop(true);
    }

    @Test
    public void bombWall() {
        model.init(map, 0, 1);
        model.start((Dimension) cubeSize.clone());
        model.addSpriteToBoard(new Wall(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height)));
        model.addSpriteToBoard(new Wall(model, new Point2D.Double(1 * cubeSize.width, 0 * cubeSize.height)));
        model.addSpriteToBoard(
                new Bomb(model, new Point2D.Double(2 * cubeSize.width, 0 * cubeSize.height), 2.0 * cubeSize.width, 0));

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 1);
        model.gameStop(true);
    }

    @Test
    public void bombBomb() {
        model.init(map, 0, 1);
        model.start((Dimension) cubeSize.clone());
        model.addSpriteToBoard(
                new Bomb(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height), 2.0 * cubeSize.width));
        model.addSpriteToBoard(
                new Bomb(model, new Point2D.Double(1 * cubeSize.width, 0 * cubeSize.height), 2.0 * cubeSize.width));
        model.addSpriteToBoard(
                new Bomb(model, new Point2D.Double(2 * cubeSize.width, 0 * cubeSize.height), 2.0 * cubeSize.width, 0));

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 3);
        model.gameStop(true);
    }

    @Test
    public void bombPlayer() {
        model.init(map, 1, 1);
        model.start((Dimension) cubeSize.clone());
        model.addSpriteToBoard(new Player(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height),
                GeneralPath.getPath() + "/picture/player/player.png"));
        model.addSpriteToBoard(
                new Bomb(model, new Point2D.Double(2 * cubeSize.width, 0 * cubeSize.height), 2.0 * cubeSize.width, 0));

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 2);
        model.gameStop(true);
    }

    @Test
    public void bombBombBooster() {
        model.init(map, 1, 1);
        model.start((Dimension) cubeSize.clone());
        model.addSpriteToBoard(new BombBooster(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height)));
        model.addSpriteToBoard(
                new Bomb(model, new Point2D.Double(2 * cubeSize.width, 0 * cubeSize.height), 2.0 * cubeSize.width, 0));

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 1);
        model.gameStop(true);
    }

    @Test
    public void bombBalloon() {
        model.init(map, 1, 1);
        model.start((Dimension) cubeSize.clone());

        model.addSpriteToBoard(
            new Balloon(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height), GeneralPath.getPath() + "/picture/monster/dino1.png")
        );

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(1 * cubeSize.width, 0 * cubeSize.height), 1.0 * cubeSize.width, 0)
        );

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 2);
        model.gameStop(true);
    }

    @Test
    public void bombBlastBooster() {
        model.init(map, 1, 1);
        model.start((Dimension) cubeSize.clone());

        model.addSpriteToBoard(
            new BlastBooster(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height))
        );

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(1 * cubeSize.width, 0 * cubeSize.height), 1.0 * cubeSize.width, 0)
        );

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 1);
        model.gameStop(true);
    }

    @Test
    public void bombDetonator() {
        model.init(map, 1, 1);
        model.start((Dimension) cubeSize.clone());

        model.addSpriteToBoard(
            new Detonator(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height))
        );

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(1 * cubeSize.width, 0 * cubeSize.height), 1.0 * cubeSize.width, 0)
        );

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 1);
        model.gameStop(true);
    }

    @Test
    public void bombInvulnerability() {
        model.init(map, 1, 1);
        model.start((Dimension) cubeSize.clone());

        model.addSpriteToBoard(
            new Invulnerability(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height))
        );

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(1 * cubeSize.width, 0 * cubeSize.height), 1.0 * cubeSize.width, 0)
        );

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 1);
        model.gameStop(true);
    }

    @Test
    public void bombRollerSkates() {
        model.init(map, 1, 1);
        model.start((Dimension) cubeSize.clone());

        model.addSpriteToBoard(
            new RollerSkates(model, new Point2D.Double(0 * cubeSize.width, 0 * cubeSize.height))
        );

        model.addSpriteToBoard(
            new Bomb(model, new Point2D.Double(1 * cubeSize.width, 0 * cubeSize.height), 1.0 * cubeSize.width, 0)
        );

        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBomb = model.getBoardSprites();
        Assert.assertEquals(afterBomb.size(), original.size() - 1);
        model.gameStop(true);
    }

}
