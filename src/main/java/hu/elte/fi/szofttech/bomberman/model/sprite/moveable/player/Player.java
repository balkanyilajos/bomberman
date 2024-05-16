package hu.elte.fi.szofttech.bomberman.model.sprite.moveable.player;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import hu.elte.fi.szofttech.bomberman.model.GameModel;
import hu.elte.fi.szofttech.bomberman.model.sprite.Sprite;
import java.util.HashSet;
import hu.elte.fi.szofttech.bomberman.model.sprite.fixedelement.Barrier;
import hu.elte.fi.szofttech.bomberman.model.sprite.moveable.MoveableSprite;
import hu.elte.fi.szofttech.bomberman.model.sprite.moveable.enemy.Balloon;
import hu.elte.fi.szofttech.bomberman.model.sprite.weapon.Bomb;
import hu.elte.fi.szofttech.bomberman.model.util.PlayerAction;
import hu.elte.fi.szofttech.bomberman.model.sprite.powerup.*;

import java.awt.geom.Area;
import java.lang.Math;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Player extends MoveableSprite {
    private int numberOfBombs = 1;
    private int numberOfPlacedBomb = 0;
    private int numberOfBarriers = 1;
    private int wonRoundNumber = 0;
    private double secTime;
    private boolean hasDetonator = false;
    private boolean hasRollerSkater = false;
    private boolean hasInvulnarability = false;
    private boolean hasGhostForm = false;
    private boolean hasBlastBooster = false;
    private boolean hasNoBomb = false;
    private boolean hasSlowMovement = false;
    private boolean hasAllBombsPlaceNow = false;
    private double sizeOfExplosion = 1;
    private double cubeSize = model.getCubeSize().getWidth();
    private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
    private Bomb lastBomb;
    // For tests
    double division;

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
        this.areaPoint = new Point2D.Double(imagePoint.getX() + 10,
                imagePoint.getY() + 10);
        this.area = new Area(
                new Ellipse2D.Double(areaPoint.getX() + model.getCubeSize().getWidth() / 4, areaPoint.getY(),
                        model.getCubeSize().getWidth() * 0.5,
                        model.getCubeSize().getHeight() * 0.8));
        this.powerUps = new ArrayList<PowerUp>();
    }

    public void usePowerUps(double deltaTime) {
        int l = powerUps.size();
        for (int i = 0; i < l;) {
            PowerUp p = powerUps.get(i);
            if (!p.decreaseTime(deltaTime)) {
                powerUps.remove(p);
                l--;
            } else {
                i++;
            }

        }
    }

    public void reset(Point2D point)
    {
        this.imagePoint = point;
        this.areaPoint = new Point2D.Double(imagePoint.getX() + 10,
                imagePoint.getY() + 10);
        this.area = new Area(
                new Ellipse2D.Double(areaPoint.getX() + model.getCubeSize().getWidth() / 4, areaPoint.getY(),
                        model.getCubeSize().getWidth() * 0.5,
                        model.getCubeSize().getHeight() * 0.8));
        unsetInvulnerability();
        unsetBlastBooster();
        unsetBombBooster();
        unsetRollerSkater();
        powerUps = new ArrayList<PowerUp>();
    }

    public void setInvulnerability(Invulnerability powerup) {
        if (hasInvulnarability) {
            return;
        }
        hasInvulnarability = true;
        powerUps.add(powerup);
    }

    public void unsetInvulnerability() {
        hasInvulnarability = false;
    }

    public void setBombBooster(BombBooster powerup) {
        if (numberOfBombs > 1) {
            return;
        }
        increaseNumberOfBombs(1);
        powerUps.add(powerup);
    }

    public void unsetBombBooster() {
        if(numberOfBombs > 1)
        {
        increaseNumberOfBombs(-1);
        }
    }

    public void setBlastBooster(BlastBooster powerup) {
        if (hasBlastBooster) {
            return;
        }
        powerUps.add(powerup);
        increaseExplosion(1);
        hasBlastBooster = true;
    }

    public void unsetBlastBooster() {
        if(hasBlastBooster)
        {
        decreaseExposion(1);
        hasBlastBooster = false;
        }
    }

    public void setDetonator(Detonator powerup) {
        if (hasDetonator) {
            return;
        }
        powerUps.add(powerup);
        hasDetonator = true;
    }

    public void unsetDetonator() {
        if(hasDetonator)
        {
        hasDetonator = false;
        detonateBombs();
        }
    }

    public boolean getInvulnerability() {
        return hasInvulnarability;
    }

    public void won()
    { wonRoundNumber++; }

    public int getWonRoundNumber()
    { return wonRoundNumber; }

    public void setGhostForm(double secTime) {
        hasGhostForm = true;
    }

    public void setRollerSkater(RollerSkates powerup) {
        if (hasRollerSkater) {
            return;
        }
        hasRollerSkater = true;
        powerUps.add(powerup);
        speed = speed * 2;
    }

    public void unsetRollerSkater() {
        if(hasRollerSkater)
        {
        hasRollerSkater = false;
        speed = speed / 2;
        }
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
            numberOfPlacedBomb++;
            Point2D center = new Point2D.Double(imagePoint.getX() + imageSize.width / 2,
                    imagePoint.getY() + imageSize.height / 2);
            Point c = model.getIndexFromCoords(center);
            Point2D bombPlace = new Point2D.Double(c.getX() * model.getCubeSize().width,
                    c.getY() * model.getCubeSize().height);
            lastBomb = new Bomb(this.model, bombPlace, sizeOfExplosion * cubeSize, 3);
            if (hasDetonator) {
                lastBomb = new Bomb(this.model, bombPlace, sizeOfExplosion * cubeSize);
            }
            // ****NOT EXPLOSING BOMB IF HASDETONATOR IS TRUE ELSE BASIC BOMB*****/
            bombs.add(lastBomb);
            // Point current = model.getIndexFromCoords(bombPlace);
            model.addSpriteToBoard(lastBomb);
            action.placeBomb = false;
        } else if (numberOfPlacedBomb == numberOfBombs && hasDetonator) {
            detonateBombs();
        }
    }

    private void detonateBombs() {
        int l = bombs.size();
        while (l > 0) {
            Bomb b = bombs.remove(0);
            b.destructor();
            l--;
        }
        numberOfPlacedBomb = 0;
        lastBomb = null;
        action.placeBomb = false;
    }

    public void updateLastBomb() {
        if (!model.getBoardSprites().contains(lastBomb) && lastBomb != null) {
            lastBomb = bombs.size() > 1 ? bombs.get(1) : null;
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

    public Point2D getImagePoint() {
        return imagePoint;
    }

    // Only for test cases.
    public void setPlayerAction(PlayerAction action) {
        this.action = action;
    }

    public double getDivisonInMove() {
        return division;
    }

    @Override
    public void destructor() {
        if (hasInvulnarability) {
            return;
        }

        detonateBombs();
        this.model.deleteSpriteFromBoard(this);
    }

    @Override
    public void move(double deltaTime) {
        Point2D previousAreaPoint = (Point2D) areaPoint.clone();
        division = 1;
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
                if (sprite instanceof PowerUp) {
                    PowerUp pu = (PowerUp) sprite;
                    pu.effect(this);
                    sprite.destructor();
                    return true;
                } else if (sprite instanceof Balloon && !hasInvulnarability) {
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
}