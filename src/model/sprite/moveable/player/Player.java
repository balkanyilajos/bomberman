package model.sprite.moveable.player;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import model.GameModel;
import model.sprite.Sprite;
import java.util.HashSet;
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
    private int numberOfBombs = 1;
    private int numberOfPlacedBomb = 0;
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
    private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
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
        this.areaPoint = new Point2D.Double(imagePoint.getX()+10,
                imagePoint.getY()+10);
        this.area = new Area(
                new Ellipse2D.Double(areaPoint.getX() + model.getCubeSize().getWidth() / 4, areaPoint.getY(),
                        model.getCubeSize().getWidth() * 0.5,
                        model.getCubeSize().getHeight() * 0.8));
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

    public void setInvulnerability(Invulnerability powerup)
    {
        hasInvulnarability = true;
        powerUps.add(powerup);
    }

    public void unsetInvulnerability()
    {
        hasInvulnarability = false;
    }

    public void setBombBooster(BombBooster powerup)
    {
        if(numberOfBombs > 1) { return; }
        increaseNumberOfBombs(1);
        powerUps.add(powerup);
    }

    public void unsetBombBooster()
    {
        increaseNumberOfBombs(-1);
    }

    public void setBlastBooster(BlastBooster powerup)
    {
        powerUps.add(powerup);
        increaseExplosion(1);
        System.out.println("S");
    }

    public void unsetBlastBooster()
    {
        System.out.println("E");
        decreaseExposion(1);
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
        if (numberOfPlacedBomb < numberOfBombs) {
            System.out.println("Lerakható: " + numberOfBombs + "\nLerakott: " +numberOfPlacedBomb);
            numberOfPlacedBomb++;
            Point temp = model.getIndexFromCoords(areaPoint);
            if (temp.y == 0) {
                temp.y += 1;
            } else if (temp.x == 0) {
                temp.x += 1;
            } else if (temp.x == model.getBoardIndexSize().width) {
                temp.x -= 1;
            } else if (temp.y == model.getBoardIndexSize().height) {
                temp.y -= 1;
            }
            Point2D bombPlace = model.getCoordsFromIndex(temp);
            lastBomb = new Bomb(this.model, bombPlace, sizeOfExplosion * cubeSize, 3);
            bombs.add(lastBomb);
            // Point current = model.getIndexFromCoords(bombPlace);
            model.addSpriteToBoard(lastBomb);
            System.out.println("Lerakható: " + numberOfBombs + "\nLerakott: " +numberOfPlacedBomb);
            action.placeBomb = false;
        }
    }

    public void updateLastBomb() {
        if (!model.getBoardSprites().contains(lastBomb) && lastBomb != null) {
            lastBomb = bombs.size()>1 ? bombs.get(1) : null;
            bombs.remove(0);
            numberOfPlacedBomb--;
            
        }
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
        Point2D previousAreaPoint = (Point2D) areaPoint.clone();
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
        // System.out.println(model.getBoardSprites(areaPoint, 1));
        if (action.placeBomb) {
            // System.out.println(model.getBoardSprites(areaPoint, 1));
            placeBomb();
        }
        model.changeSpriteMovementOnBoard(this, previousAreaPoint);
        // System.out.println(model.getBoardSprites(areaPoint, 1));
    }

    @Override
    public boolean isMoveable(Point2D point) {
        HashSet<Sprite> sprites = model.getBoardSprites(point, model.getCubeSize().getWidth());
        sprites.remove(this);
        // System.out.println(sprites);
        if (lastBomb != null && isIntersect(lastBomb.getArea())) {
            sprites.remove(lastBomb);
        }
        // System.out.println("After: " + sprites);
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
        updateLastBomb();
        move(deltaTime);
        usePowerUps(deltaTime);
    }

    @Override
    public void draw(Graphics graphics) {
        super.draw(graphics);
        Graphics2D gr = (Graphics2D) graphics;
        gr.fill(area);
    }

}