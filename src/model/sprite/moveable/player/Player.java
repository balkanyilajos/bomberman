package model.sprite.moveable.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.awt.Dimension;
import java.awt.Image;

import model.GameModel;
import model.sprite.Sprite;
import model.sprite.fixedelement.Barrier;
import model.sprite.moveable.MoveableSprite;
import model.sprite.weapon.Bomb;
import model.util.Action;
import model.util.PlayerAction;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

public class Player extends MoveableSprite {
    private int numberOfBombs = 3;
    private int numberOfBarriers = 1;
    private double secTime;
    private boolean hasDetonator = false;
    private boolean hasRollerSkater = false;
    private boolean hasInvulnarability = false;
    private boolean hasGhostForm = false;
    private boolean hasNoBomb = false;
    private boolean hasSlowMovement = false;
    private boolean hasAllBombsPlaceNow = false;
    private double sizeOfExplosion;
    private HashSet<Bomb> bombs;

    public Player(GameModel model, Point2D imagePoint, String imagePath) {

        super(model, new Area(new Ellipse2D.Double(imagePoint.getX(), imagePoint.getY(), model.getCubeSize().getWidth(),
                model.getCubeSize().getHeight())), null, 100, imagePoint, null, model.getCubeSize(), imagePath);
        this.action = new PlayerAction();
        this.areaPoint = new Point2D.Double(imagePoint.getX() + model.getCubeSize().getWidth() * 0.1,
                imagePoint.getY() + model.getCubeSize().getHeight() * 0.1);
        action.up = true;
    }

    private Player(GameModel model, Area area, PlayerAction action, Point2D imagePoint, Point2D areaPoint,
            Dimension imageSize,
            String imagePath, int speed) {
        super(model, null, action, speed, imagePoint, areaPoint, imageSize, imagePath);
        this.area = new Area(new Ellipse2D.Double(imagePoint.getX(), imagePoint.getY(), imageSize.getWidth(),
                imageSize.getHeight()));
        this.sizeOfExplosion = 3;
    }

    public void setUp() {
        action.up = true;
    }

    public void setInvulnerability(double secTime) {
        hasInvulnarability = true;
    }

    public void setGhostForm(double secTime) {
        hasGhostForm = true;
    }

    public void setDetonator() {
        hasDetonator = true;
    }

    public void setRollerSkater() {
        hasRollerSkater = true;
    }

    public void setNoBomb(double secTime) {
        hasNoBomb = true;
    }

    public void slowMovement() {
        hasSlowMovement = true;
    }

    public void setAllBombsPlaceNow() {
        hasAllBombsPlaceNow = true;
    }

    private void placeBomb() {
        numberOfBombs--;
        // Bomb bomb = new Bomb(this.model, this.areaPoint, this.sizeOfExplosion, 2);
        // bombs.add(bomb);
    }

    public void increaseNumberOfBombs(int numberOfNewBombs) {
        numberOfBombs += numberOfNewBombs;
    }

    public void increaseExplosion(int numberOfCubes) {
        sizeOfExplosion += numberOfCubes;
    }

    public void decreaseExposion(int numberOfCubes) {
        sizeOfExplosion -= numberOfCubes;
    }

    private void palceBarrier() {
        numberOfBarriers--;
        Barrier barrier = new Barrier(model, imagePoint);
    }

    @Override
    public void move(double deltaTime) {
        double division = 1;
        if (action.up && action.left || action.up && action.right || action.down && action.left
                || action.down && action.right) {
            division = Math.sqrt(2);
        }

        if (action.up) {
            Point2D newAreaPoint = new Point2D.Double(areaPoint.getX(),
                    areaPoint.getY() - deltaTime * speed / division);
            Point2D newImagePoint = new Point2D.Double(imagePoint.getX(),
                    imagePoint.getY() - deltaTime * speed / division);
            Area newArea = new Area(
                    new Ellipse2D.Double(imagePoint.getX(), imagePoint.getY(), model.getCubeSize().getWidth(),
                            model.getCubeSize().getHeight()));
            if (isMoveable(newAreaPoint)) {
                areaPoint = newAreaPoint; // új area és imagePoint létrehozása
            }

            if (isMoveable(newImagePoint)) {
                imagePoint = newImagePoint;
            }

            if (!isIntersect(newArea)) {
                area = newArea;
            }
        }

        if (action.down) {
            Point2D newAreaPoint = new Point2D.Double(areaPoint.getX(),
                    areaPoint.getY() + deltaTime * speed / division);
            if (isMoveable(newAreaPoint)) {
                areaPoint = newAreaPoint; // új area és imagePoint létrehozása
            }
        }

        if (action.left) {
            Point2D newPoint = new Point2D.Double(areaPoint.getX() + deltaTime * speed / division, areaPoint.getY());
            if (isMoveable(newPoint)) {
                areaPoint = newPoint; // új area és imagePoint létrehozása
            }
        }

        if (action.right) {
            Point2D newPoint = new Point2D.Double(areaPoint.getX() - deltaTime * speed / division, areaPoint.getY());
            if (isMoveable(newPoint)) {
                areaPoint = newPoint; // új area és imagePoint létrehozása
            }
        }
    }

    @Override
    public boolean isMoveable(Point2D point) {
        HashSet<Sprite> sprites = model.getBoardSprites(point, 1);
        for (Sprite sprite : sprites) {
            if (isIntersect(sprite)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void update(double deltaTime) {
        move(deltaTime);
        placeBomb();
    }
}