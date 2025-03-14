package hu.elte.fi.szofttech.bomberman.model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.*;
import org.junit.jupiter.api.Test;

import hu.elte.fi.szofttech.bomberman.model.GameModel;
import hu.elte.fi.szofttech.bomberman.model.MapReader;
import hu.elte.fi.szofttech.bomberman.model.sprite.*;
import hu.elte.fi.szofttech.bomberman.model.sprite.fixedelement.*;
import hu.elte.fi.szofttech.bomberman.model.sprite.moveable.enemy.Balloon;
import hu.elte.fi.szofttech.bomberman.model.sprite.moveable.player.Player;
import hu.elte.fi.szofttech.bomberman.model.sprite.powerup.*;
import hu.elte.fi.szofttech.bomberman.model.sprite.weapon.Bomb;
import hu.elte.fi.szofttech.bomberman.model.util.PlayerAction;
import hu.elte.fi.szofttech.bomberman.GeneralPath;

public class aPlayerTest {
    private final GameModel model;
    private final Dimension cubeSize;
    private String map;
    private String playerImage;

    public aPlayerTest() {
        map = "src/test/java/hu/elte/fi/szofttech/bomberman/model/playerMap.txt";
        playerImage = GeneralPath.getPath() + "/picture/player/gamer.png";
        model = new GameModel(null, map, 1, new ArrayList<>());
        cubeSize = new Dimension(50, 50);
    }

    @Test
    public void moveUp() {
        model.start((Dimension) cubeSize.clone());
        PlayerAction action = new PlayerAction(true, false, false, false);

        Player player = new Player(model, action, new Point2D.Double(4 * cubeSize.width, 4 * cubeSize.height),
                playerImage);
        model.addSpriteToBoard(player);
        double firstImagePointOfPlayer = player.getImagePoint().getY();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double secondImagePointOfPlayer = player.getImagePoint().getY();

        Assert.assertTrue(firstImagePointOfPlayer > secondImagePointOfPlayer);
        model.gameStop(true);
    }

    @Test
    public void moveDown() {
        model.start((Dimension) cubeSize.clone());
        PlayerAction action = new PlayerAction(false, true, false, false);

        Player player = new Player(model, action, new Point2D.Double(4 * cubeSize.width, 4 * cubeSize.height),
                playerImage);
        model.addSpriteToBoard(player);
        double firstImagePointOfPlayer = player.getImagePoint().getY();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double secondImagePointOfPlayer = player.getImagePoint().getY();

        Assert.assertTrue(firstImagePointOfPlayer < secondImagePointOfPlayer);
        model.gameStop(true);
    }

    @Test
    public void moveRight() {
        model.start((Dimension) cubeSize.clone());
        PlayerAction action = new PlayerAction(false, false, false, true);

        Player player = new Player(model, action, new Point2D.Double(4 * cubeSize.width, 4 * cubeSize.height),
                playerImage);
        model.addSpriteToBoard(player);
        double firstImagePointOfPlayer = player.getImagePoint().getX();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double secondImagePointOfPlayer = player.getImagePoint().getX();

        Assert.assertTrue(firstImagePointOfPlayer < secondImagePointOfPlayer);
        model.gameStop(true);
    }

    @Test
    public void moveLeft() {
        model.start((Dimension) cubeSize.clone());
        PlayerAction action = new PlayerAction(false, false, true, false);

        Player player = new Player(model, action, new Point2D.Double(4 * cubeSize.width, 4 * cubeSize.height),
                playerImage);
        model.addSpriteToBoard(player);
        double firstImagePointOfPlayer = player.getImagePoint().getX();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double secondImagePointOfPlayer = player.getImagePoint().getX();

        Assert.assertTrue(firstImagePointOfPlayer > secondImagePointOfPlayer);
        model.gameStop(true);
    }

    // Diagonal movements

    @Test
    public void moveUpLeft() {
        model.start((Dimension) cubeSize.clone());
        PlayerAction action = new PlayerAction(true, false, true, false);

        Player player = new Player(model, action, new Point2D.Double(4 * cubeSize.width, 4 * cubeSize.height),
                playerImage);
        model.addSpriteToBoard(player);
        double firstImagePointOfPlayerX = player.getImagePoint().getX();
        double firstImagePointOfPlayerY = player.getImagePoint().getY();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double secondImagePointOfPlayerX = player.getImagePoint().getX();
        double secondImagePointOfPlayerY = player.getImagePoint().getY();

        Assert.assertTrue(firstImagePointOfPlayerX > secondImagePointOfPlayerX
                && firstImagePointOfPlayerY > secondImagePointOfPlayerY);
        model.gameStop(true);
    }

    @Test
    public void moveUpRight() {
        model.start((Dimension) cubeSize.clone());
        PlayerAction action = new PlayerAction(true, false, false, true);

        Player player = new Player(model, action, new Point2D.Double(4 * cubeSize.width, 4 * cubeSize.height),
                playerImage);
        model.addSpriteToBoard(player);
        double firstImagePointOfPlayerX = player.getImagePoint().getX();
        double firstImagePointOfPlayerY = player.getImagePoint().getY();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double secondImagePointOfPlayerX = player.getImagePoint().getX();
        double secondImagePointOfPlayerY = player.getImagePoint().getY();

        Assert.assertTrue(firstImagePointOfPlayerX < secondImagePointOfPlayerX
                && firstImagePointOfPlayerY > secondImagePointOfPlayerY);
        model.gameStop(true);
    }

    @Test
    public void moveDownLeft() {
        model.start((Dimension) cubeSize.clone());
        PlayerAction action = new PlayerAction(false, true, true, false);

        Player player = new Player(model, action, new Point2D.Double(4 * cubeSize.width, 4 * cubeSize.height),
                playerImage);
        model.addSpriteToBoard(player);
        double firstImagePointOfPlayerX = player.getImagePoint().getX();
        double firstImagePointOfPlayerY = player.getImagePoint().getY();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double secondImagePointOfPlayerX = player.getImagePoint().getX();
        double secondImagePointOfPlayerY = player.getImagePoint().getY();

        Assert.assertTrue(firstImagePointOfPlayerX > secondImagePointOfPlayerX
                && firstImagePointOfPlayerY < secondImagePointOfPlayerY);
        model.gameStop(true);
    }

    @Test
    public void moveDownRight() {
        model.start((Dimension) cubeSize.clone());
        PlayerAction action = new PlayerAction(false, true, false, true);

        Player player = new Player(model, action, new Point2D.Double(4 * cubeSize.width, 4 * cubeSize.height),
                playerImage);
        model.addSpriteToBoard(player);
        double firstImagePointOfPlayerX = player.getImagePoint().getX();
        double firstImagePointOfPlayerY = player.getImagePoint().getY();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double secondImagePointOfPlayerX = player.getImagePoint().getX();
        double secondImagePointOfPlayerY = player.getImagePoint().getY();

        Assert.assertTrue(firstImagePointOfPlayerX < secondImagePointOfPlayerX
                && firstImagePointOfPlayerY < secondImagePointOfPlayerY);
        model.gameStop(true);
    }

    @Test
    public void speedOneDirection() {
        model.start((Dimension) cubeSize.clone());
        PlayerAction action = new PlayerAction(true, false, false, false);

        Player player = new Player(model, action, new Point2D.Double(4 * cubeSize.width, 4 * cubeSize.height),
                playerImage);
        model.addSpriteToBoard(player);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(player.getDivisonInMove() == 1);
        model.gameStop(true);
    }

    @Test
    public void speedTwoDirection() {
        model.start((Dimension) cubeSize.clone());
        PlayerAction action = new PlayerAction(true, false, true, false);

        Player player = new Player(model, action, new Point2D.Double(4 * cubeSize.width, 4 * cubeSize.height),
                playerImage);
        model.addSpriteToBoard(player);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(player.getDivisonInMove() == Math.sqrt(2));
        model.gameStop(true);
    }

    // Bomb tests

    @Test
    public void placeBomb() {
        model.start((Dimension) cubeSize.clone());
        PlayerAction action = new PlayerAction(false, false, true, false, true, false);

        Player player = new Player(model, action, new Point2D.Double(4 * cubeSize.width, 4 * cubeSize.height),
                playerImage);
        model.addSpriteToBoard(player);
        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBombPlace = model.getBoardSprites();
        Assert.assertNotEquals(original, afterBombPlace);

        model.gameStop(true);
    }

    @Test
    public void moveOverBomb() {
        model.start((Dimension) cubeSize.clone());
        PlayerAction action = new PlayerAction(false, false, false, true, false, false);

        Player player = new Player(model, action, new Point2D.Double(4 * cubeSize.width, 4 * cubeSize.height),
                playerImage);
        model.addSpriteToBoard(player);

        Bomb bomb = new Bomb(model, new Point2D.Double(5 * cubeSize.width, 4 * cubeSize.height), 0);
        model.addSpriteToBoard(bomb);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double playerPosition = player.getImagePoint().getX();
        Assert.assertTrue(playerPosition < 5 * cubeSize.width);

        model.gameStop(true);
    }

    @Test
    public void moveFromBomb() {
        model.start((Dimension) cubeSize.clone());
        PlayerAction action = new PlayerAction(false, false, false, true, true, false);

        Player player = new Player(model, action, new Point2D.Double(4 * cubeSize.width, 4 * cubeSize.height),
                playerImage);
        model.addSpriteToBoard(player);
        double firstImagePointOfPlayerX = player.getImagePoint().getX();
        HashSet<Sprite> original = model.getBoardSprites();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashSet<Sprite> afterBombPlace = model.getBoardSprites();
        double secondImagePointOfPlayerX = player.getImagePoint().getX();

        Assert.assertNotEquals(original, afterBombPlace);
        Assert.assertTrue(firstImagePointOfPlayerX < secondImagePointOfPlayerX);

        model.gameStop(true);
    }

    @Test
    public void goBackToBomb() {
        model.start((Dimension) cubeSize.clone());
        PlayerAction action = new PlayerAction(false, false, false, true, true, false);

        Player player = new Player(model, action, new Point2D.Double(4 * cubeSize.width, 4 * cubeSize.height),
                playerImage);
        model.addSpriteToBoard(player);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        action.placeBomb = false;
        action.right = false;
        action.left = true;
        player.setPlayerAction(action);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double playerPosition = player.getImagePoint().getX();
        Assert.assertTrue(playerPosition > 4 * cubeSize.width);

        model.gameStop(true);
    }
}
