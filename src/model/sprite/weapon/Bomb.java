package model.sprite.weapon;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.*;

import model.GameModel;
import model.sprite.Sprite;
import model.sprite.fixedelement.Box;
import model.sprite.fixedelement.Wall;
import model.util.Action;

public class Bomb extends Sprite {

    private double radius;
    private Dimension size;
    private boolean isDestroyed;
    private Action bombAction;
    private int startFlameIndex;
    private ArrayList<Flame> flameUp;
    private ArrayList<Flame> flameDown;
    private ArrayList<Flame> flameRight;
    private ArrayList<Flame> flameLeft;
    private ScheduledExecutorService executorService;

    public Bomb(GameModel model, Point2D point, double radius, int bombSeconds) {
        this(model, point, radius);
        executorService.schedule(() -> destructor(), bombSeconds, TimeUnit.SECONDS);
    }

    public Bomb(GameModel model, Point2D point, double radius) {
        this(model, point, radius, model.getCubeSize());
    }

    private Bomb(GameModel model, Point2D imagePoint, double radius, Dimension size) {
        super(model, null, imagePoint, null, size, "src/data/picture/bomb.png");
        this.areaPoint = new Point2D.Double(imagePoint.getX() + size.getWidth() * 0.1,
                imagePoint.getY() + size.getHeight() * 0.1);
        this.area = new Area(new Ellipse2D.Double(areaPoint.getX(), areaPoint.getY(), size.getWidth() * 0.8,
                size.getHeight() * 0.8));
        this.radius = radius;
        this.size = size;
        this.isDestroyed = false;
        this.bombAction = new Action(true, true, true, true);
        this.startFlameIndex = 0;
        this.flameUp = new ArrayList<>();
        this.flameDown = new ArrayList<>();
        this.flameRight = new ArrayList<>();
        this.flameLeft = new ArrayList<>();
        flameUp.add(new Flame(model, getAreaPoint(), new Action(true, false, false, false)));
        flameDown.add(new Flame(model, getAreaPoint(), new Action(false, true, false, false)));
        flameRight.add(new Flame(model, getAreaPoint(), new Action(false, false, false, true)));
        flameLeft.add(new Flame(model, getAreaPoint(), new Action(false, false, true, false)));
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public boolean getIsDistroyed() {
        return isDestroyed;
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
        int addPixel = 5;
        HashSet<Sprite> sprites = model.getBoardSprites(areaPoint, radius);
        sprites.remove(this);

        bombAction.right = bombAction.right && doFlameAction(sprites, new Dimension(addPixel, 0), flameRight, false, new Point2D.Double(radius + areaPoint.getX() + 3*addPixel, Double.MAX_VALUE), new Action(false, false, false, true));
        bombAction.left = bombAction.left && doFlameAction(sprites, new Dimension(-addPixel, 0), flameLeft, true, new Point2D.Double(areaPoint.getX() - radius - 3*addPixel, Double.MIN_VALUE), new Action(false, false, true, false));
        bombAction.down = bombAction.down && doFlameAction(sprites, new Dimension(0, addPixel), flameDown, false, new Point2D.Double(Double.MAX_VALUE, radius + areaPoint.getY() + 3*addPixel), new Action(false, true, false, false));
        bombAction.up = bombAction.up && doFlameAction(sprites, new Dimension(0, -addPixel), flameUp, true, new Point2D.Double(Double.MIN_VALUE, areaPoint.getY() - radius - 3*addPixel), new Action(true, false, false, false));
      
        
        if(!bombAction.any()) {
            if(flameUp.size() >= startFlameIndex || flameDown.size() >= startFlameIndex ||
            flameRight.size() >= startFlameIndex || flameLeft.size() >= startFlameIndex) {
                startFlameIndex += 1;
            }
            else {
                return;
            }
        }
        
        executorService.schedule(() -> destructor(), 5, TimeUnit.MILLISECONDS);
    }

    private boolean doFlameAction(HashSet<Sprite> sprites, Dimension addPixel, ArrayList<Flame> flames, boolean isGreaterThanToRange, Point2D toRange, Action direction) {
        Flame lastFlame = flames.get(flames.size()-1);
        Point2D.Double coord = (Point2D.Double)lastFlame.getImagePoint();
        coord.x += addPixel.getWidth();
        coord.y += addPixel.getHeight();
        for(Flame flame : flames) {
            for(Sprite sprite : sprites) {
                if(isGreaterThanToRange) {
                    if(coord.getX() > toRange.getX() && coord.getY() > toRange.getY()) {
                        if(flame.isIntersect(sprite)) {
                            if(eliminateSprite(sprite)) return false;
                        }
                    }
                    else {
                        return false;
                    }
                }
                else {
                    if(coord.getX() < toRange.getX() && coord.getY() < toRange.getY()) {
                        if(flame.isIntersect(sprite)) {
                            if(eliminateSprite(sprite)) return false;
                        }                        
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        flames.add(new Flame(model, coord, direction));
        return true;
    }
    
    private boolean eliminateSprite(Sprite sprite) {
        if(sprite instanceof Wall) {
            return true;
        }
        else if (sprite instanceof Bomb) {
            if(!((Bomb)sprite).getIsDistroyed()) {
                sprite.destructor();
            }
            return true;
        }
        else {
            sprite.destructor();
            if (sprite instanceof Box) {
                return true;
            }
        }
        return false;
    }

}
