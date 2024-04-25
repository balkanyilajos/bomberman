package model.sprite.moveable.player;

import java.util.HashSet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import model.GameModel;
import model.sprite.Sprite;
import model.sprite.fixedelement.Barrier;
import model.sprite.moveable.MoveableSprite;
import model.sprite.moveable.enemy.Balloon;
import model.sprite.weapon.Bomb;
import model.util.PlayerAction;
import model.sprite.powerup.*;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

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
    private double sizeOfExplosion = 1;
    private double cubeSize = model.getCubeSize().getWidth();
    private HashSet<Bomb> bombs = new HashSet<Bomb>();
    private Bomb lastBomb;

    private ArrayList<PowerUp> powerUps;

    public Player(GameModel model, Point2D imagePoint, String imagePath) {
        this(model, new PlayerAction(), imagePoint, imagePath);
    }

    public Player(GameModel model, PlayerAction action, Point2D imagePoint, String imagePath) {
        this(model, action, imagePoint, model.getCubeSize(), imagePath, 150);
    }

    private Player(GameModel model, PlayerAction action, Point2D imagePoint, Dimension imageSize, String imagePath,
            int speed) {
        super(model, null, action, speed, imagePoint, null, imageSize, imagePath);
        this.areaPoint = new Point2D.Double(imagePoint.getX(),
                imagePoint.getY());
        this.area = new Area(
                new Ellipse2D.Double(areaPoint.getX() + model.getCubeSize().getWidth() / 4, areaPoint.getY(),
                        model.getCubeSize().getWidth() * 0.5,
                        model.getCubeSize().getHeight() * 0.8));
        this.sizeOfExplosion = 3;
        this.powerUps = new ArrayList<PowerUp>();
    }

    public void usePowerUps(double deltaTime)
    {
        int l = powerUps.size();
        for (int i = 0; i<l;)
        {
            PowerUp p = powerUps.get(i);
            if(!p.decreaseTime(deltaTime))
            {
                powerUps.remove(p);
                l--;
            }
            else
            {
                i++;
            }

        }
    }

    public void setInvulnerability(BombBooster powerup)
    {
        hasInvulnarability = true;
        powerUps.add(powerup);
    }

    public void unsetInvulnerability()
    {
        hasInvulnarability = false;
    }

    public boolean getInvulnerability() {
        return hasInvulnarability;
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

    private void placeBarrier() {
        numberOfBarriers--;
        Barrier barrier = new Barrier(model, imagePoint);
    }

    @Override
    public void move(double deltaTime) {
        Point2D previousAreaPoint = (Point2D)areaPoint.clone();
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
                    new Ellipse2D.Double(areaPoint.getX() + model.getCubeSize().getWidth() / 4, areaPoint.getY(),
                            model.getCubeSize().getWidth() * 0.5,
                            model.getCubeSize().getHeight() * 0.8));
            if (isMoveable(newAreaPoint)) {
                areaPoint = newAreaPoint;
                imagePoint = newImagePoint;
                area = newArea;
            }
        }

        if (action.down) {
            Point2D newAreaPoint = new Point2D.Double(areaPoint.getX(),
                    areaPoint.getY() + deltaTime * speed / division);
            Point2D newImagePoint = new Point2D.Double(imagePoint.getX(),
                    imagePoint.getY() + deltaTime * speed / division);
            Area newArea = new Area(
                    new Ellipse2D.Double(areaPoint.getX() + model.getCubeSize().getWidth() / 4, areaPoint.getY(),
                            model.getCubeSize().getWidth() * 0.5,
                            model.getCubeSize().getHeight() * 0.8));
            if (isMoveable(newAreaPoint)) {
                areaPoint = newAreaPoint;
                imagePoint = newImagePoint;
                area = newArea;
            }
        }

        if (action.left) {
            Point2D newAreaPoint = new Point2D.Double(areaPoint.getX() - deltaTime * speed / division,
                    areaPoint.getY());
            Point2D newImagePoint = new Point2D.Double(imagePoint.getX() - deltaTime * speed / division,
                    imagePoint.getY());
            Area newArea = new Area(
                    new Ellipse2D.Double(areaPoint.getX() + model.getCubeSize().getWidth() / 4, areaPoint.getY(),
                            model.getCubeSize().getWidth() * 0.5,
                            model.getCubeSize().getHeight() * 0.8));
            if (isMoveable(newAreaPoint)) {
                areaPoint = newAreaPoint;
                imagePoint = newImagePoint;
                area = newArea;
            }
        }

        if (action.right) {
            Point2D newAreaPoint = new Point2D.Double(areaPoint.getX() + deltaTime * speed / division,
                    areaPoint.getY());
            Point2D newImagePoint = new Point2D.Double(imagePoint.getX() + deltaTime * speed / division,
                    imagePoint.getY());
            Area newArea = new Area(
                    new Ellipse2D.Double(areaPoint.getX() + model.getCubeSize().getWidth() / 4, areaPoint.getY(),
                            model.getCubeSize().getWidth() * 0.5,
                            model.getCubeSize().getHeight() * 0.8));
            if (isMoveable(newAreaPoint)) {
                areaPoint = newAreaPoint;
                imagePoint = newImagePoint;
                area = newArea;
            }
        }
        model.changeSpriteMovementOnBoard(this, previousAreaPoint);
    }

    @Override
    public boolean isMoveable(Point2D point) {
        HashSet<Sprite> sprites = model.getBoardSprites(point, model.getCubeSize().getWidth());
        sprites.remove(this);
        for (Sprite sprite : sprites) {
            if (isIntersect(sprite, point)) {
                if(sprite instanceof PowerUp)
                {
                    PowerUp pu = (PowerUp) sprite;
                    pu.effect(this);
                    sprite.destructor();
                    return true;
                }
                else if(sprite instanceof Balloon && !hasInvulnarability)
                {
                        destructor();
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void update(double deltaTime) {
        move(deltaTime);
        //placeBomb();
        usePowerUps(deltaTime);
    }

    @Override
    public void draw(Graphics graphics) {
        super.draw(graphics);
        Graphics2D gr = (Graphics2D) graphics;
        gr.fill(area);
    }

}