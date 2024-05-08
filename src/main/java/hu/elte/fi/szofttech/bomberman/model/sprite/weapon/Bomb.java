package hu.elte.fi.szofttech.bomberman.model.sprite.weapon;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.HashSet;

import hu.elte.fi.szofttech.bomberman.model.GameModel;
import hu.elte.fi.szofttech.bomberman.model.sprite.Sprite;
import hu.elte.fi.szofttech.bomberman.model.sprite.fixedelement.Barrier;
import hu.elte.fi.szofttech.bomberman.model.sprite.fixedelement.Box;
import hu.elte.fi.szofttech.bomberman.model.sprite.fixedelement.Wall;
import hu.elte.fi.szofttech.bomberman.model.sprite.moveable.player.Player;
import hu.elte.fi.szofttech.bomberman.model.sprite.powerup.*;
import hu.elte.fi.szofttech.bomberman.model.util.Action;
import hu.elte.fi.szofttech.bomberman.GeneralPath;

public class Bomb extends Sprite {
    private double radius;
    private int radiusIndex;
    private Dimension size;

    private boolean isDestroyed = false;
    private Action bombAction = new Action(true, true, true, true);
    private int startFlameIndex = 0;
    private ArrayList<Flame> flameUp = new ArrayList<>();
    private ArrayList<Flame> flameDown = new ArrayList<>();
    private ArrayList<Flame> flameRight = new ArrayList<>();
    private ArrayList<Flame> flameLeft = new ArrayList<>();

    private double explosionSeconds;
    private double flameSeconds = 0.04;
    private double elapsedTime = 0;

    public Bomb(GameModel model, Point2D point, double radius, double explosionSeconds) {
        this(model, point, radius, model.getCubeSize(), explosionSeconds);
    }

    public Bomb(GameModel model, Point2D point, double radius) {
        this(model, point, radius, model.getCubeSize(), -1);
    }

    private Bomb(GameModel model, Point2D imagePoint, double radius, Dimension size, double explosionSeconds) {
        super(model, null, imagePoint, null, size, GeneralPath.getPath() + "/picture/bomb/bomb.png");
        this.areaPoint = new Point2D.Double(imagePoint.getX() + size.getWidth() * 0.1,
                imagePoint.getY() + size.getHeight() * 0.1);
        this.area = new Area(new Ellipse2D.Double(areaPoint.getX(), areaPoint.getY(), size.getWidth() * 0.8,
                size.getHeight() * 0.8));
        this.radius = radius;
        this.size = size;

        flameUp.add(new Flame(model, getAreaPoint(), new Action(true, false, false, false)));
        flameDown.add(new Flame(model, getAreaPoint(), new Action(false, true, false, false)));
        flameRight.add(new Flame(model, getAreaPoint(), new Action(false, false, false, true)));
        flameLeft.add(new Flame(model, getAreaPoint(), new Action(false, false, true, false)));

        this.explosionSeconds = explosionSeconds;
        this.radiusIndex = (int) (radius / model.getCubeSize().width);
    }

    @Override
    public void draw(Graphics graphics) {
        if (isDestroyed) {
            for (int i = startFlameIndex; i < flameUp.size(); i++) {
                flameUp.get(i).draw(graphics);
            }
            for (int i = startFlameIndex; i < flameDown.size(); i++) {
                flameDown.get(i).draw(graphics);
            }
            for (int i = startFlameIndex; i < flameLeft.size(); i++) {
                flameLeft.get(i).draw(graphics);
            }
            for (int i = startFlameIndex; i < flameRight.size(); i++) {
                flameRight.get(i).draw(graphics);
            }
        } else {
            graphics.drawImage(actualImage, (int) imagePoint.getX(), (int) imagePoint.getY(), (int) size.getWidth(),
                    (int) size.getHeight(), null);
        }
    }

    @Override
    public void destructor() {
        isDestroyed = true;
        if (elapsedTime < flameSeconds) {
            return;
        }

        elapsedTime = 0;
        if (bombAction.any()) {
            // the explosion slows down
            flameSeconds *= 1 + 0.2 / radiusIndex;
        } else {
            if (flameUp.size() >= startFlameIndex || flameDown.size() >= startFlameIndex ||
                    flameRight.size() >= startFlameIndex || flameLeft.size() >= startFlameIndex) {
                startFlameIndex += 1;
            } else {
                super.destructor();
            }
        }

        int addPixel = (int) (model.getCubeSize().width * 0.3);
        HashSet<Sprite> sprites = model.getBoardSprites(areaPoint, radius);
        sprites.remove(this);

        bombAction.right = bombAction.right & doFlameAction(sprites, new Dimension(addPixel, 0), flameRight, false,
                new Point2D.Double(radius + areaPoint.getX() + addPixel, Double.MAX_VALUE),
                new Action(false, false, false, bombAction.right));
        bombAction.left = bombAction.left & doFlameAction(sprites, new Dimension(-addPixel, 0), flameLeft, true,
                new Point2D.Double(areaPoint.getX() - radius - addPixel, Double.MIN_VALUE),
                new Action(false, false, bombAction.left, false));
        bombAction.down = bombAction.down & doFlameAction(sprites, new Dimension(0, addPixel), flameDown, false,
                new Point2D.Double(Double.MAX_VALUE, radius + areaPoint.getY() + addPixel),
                new Action(false, bombAction.down, false, false));
        bombAction.up = bombAction.up & doFlameAction(sprites, new Dimension(0, -addPixel), flameUp, true,
                new Point2D.Double(Double.MIN_VALUE, areaPoint.getY() - radius - addPixel),
                new Action(bombAction.up, false, false, false));

    }

    @Override
    public void update(double deltaTime) {
        if (isDestroyed || explosionSeconds != -1) {
            elapsedTime += deltaTime;
        }

        if (isDestroyed) {
            destructor();
            return;
        }

        if (elapsedTime >= explosionSeconds && explosionSeconds != -1) {
            elapsedTime -= explosionSeconds;
            destructor();
        }
    }

    private boolean doFlameAction(HashSet<Sprite> sprites, Dimension addPixel, ArrayList<Flame> flames,
            boolean isGreaterThanToRange, Point2D toRange, Action direction) {
        Flame lastFlame = flames.get(flames.size() - 1);
        Point2D.Double coord = (Point2D.Double) lastFlame.getImagePoint();
        coord.x += addPixel.getWidth();
        coord.y += addPixel.getHeight();
        for (Flame flame : flames) {
            for (Sprite sprite : sprites) {
                if (flame.isIntersect(sprite)) {
                    if (eliminateSprite(sprite))
                        return false;
                }
            }
        }

        boolean isOverRange = (isGreaterThanToRange) ? coord.getX() < toRange.getX() || coord.getY() < toRange.getY()
                : coord.getX() > toRange.getX() || coord.getY() > toRange.getY();
        if (isOverRange || !direction.any()) {
            return false;
        }

        flames.add(new Flame(model, coord, direction));
        return true;
    }

    private boolean eliminateSprite(Sprite sprite) {
        if (sprite instanceof Wall) {
            return true;
        }

        if (!(sprite instanceof PowerUp)) {
            sprite.destructor();
        }

        if (sprite instanceof Box || sprite instanceof Barrier || sprite instanceof Bomb) {
            return true;
        }

        return false;
    }

}
