package model.sprite.weapon;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.*;

import model.GameModel;
import model.sprite.Sprite;
import model.util.Action;

public class Bomb extends Sprite {

    private double radius;
    private Dimension size;
    private boolean isDestroyed;
    private int startFlameIndex;
    private ArrayList<Flame> flameUp;
    private ArrayList<Flame> flameDown;
    private ArrayList<Flame> flameRight;
    private ArrayList<Flame> flameLeft;
    private ScheduledExecutorService executorService;

    public Bomb(GameModel model, Point2D point, double radius, int bombSeconds) throws IOException {
        this(model, point, radius);
        executorService.schedule(() -> isDestroyed = true, bombSeconds, TimeUnit.SECONDS);
        executorService.schedule(() -> destructor(), bombSeconds, TimeUnit.SECONDS);
    }

    public Bomb(GameModel model, Point2D point, double radius) throws IOException {
        this(model, point, radius, model.getCubeSize());
    }

    private Bomb(GameModel model,Point2D imagePoint, double radius, Dimension size) throws IOException {
        super(model, null, imagePoint, null, size, "src/data/picture/bomb.png");
        this.areaPoint = new Point2D.Double(imagePoint.getX() + size.getWidth()*0.1, imagePoint.getY() + size.getHeight()*0.1);
        this.area = new Area(new Ellipse2D.Double(areaPoint.getX(), areaPoint.getY(), size.getWidth()*0.8, size.getHeight()*0.8));
        this.radius = radius;
        this.size = size;
        this.isDestroyed = false;
        this.startFlameIndex = 0;
        this.flameUp = new ArrayList<>();
        this.flameDown = new ArrayList<>();
        this.flameRight = new ArrayList<>();
        this.flameLeft = new ArrayList<>();
        flameUp.add(new Flame(model, areaPoint, new Action(true, false, false, false)));
        flameDown.add(new Flame(model, areaPoint, new Action(false, true, false, false)));
        flameRight.add(new Flame(model, areaPoint, new Action(false, false, false, true)));
        flameLeft.add(new Flame(model, areaPoint, new Action(false, false, true, false)));
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void draw(Graphics graphics) {
        if(isDestroyed) {
            for(int i = startFlameIndex; i < flameUp.size(); i++) {
                flameUp.get(i).draw(graphics);
            }
            for(int i = startFlameIndex; i < flameDown.size(); i++) {
                flameDown.get(i).draw(graphics);
            }
            for(int i = startFlameIndex; i < flameLeft.size(); i++) {
                flameLeft.get(i).draw(graphics);
            }
            for(int i = startFlameIndex; i < flameRight.size(); i++) {
                flameRight.get(i).draw(graphics);
            }
        }
        else {
            graphics.drawImage(actualImage, (int)imagePoint.getX(), (int)imagePoint.getY(), (int)size.getWidth(), (int)size.getHeight(), null);
        }
    }

    @Override
    public void destructor() {
        final int ADD_PIXEL = 5;
        boolean hasChange = false;

        HashSet<Sprite> sprites = model.getBoardSprites(areaPoint, radius);
        sprites.remove(this);

        Point2D.Double coord = (Point2D.Double)flameRight.get(flameRight.size()-1).getImagePoint();
        coord.x += ADD_PIXEL;
        if(!sprites.stream().anyMatch(n -> flameRight.get(flameRight.size()-1).isIntersect(n)) && coord.getX() < radius + areaPoint.getX() + 3*ADD_PIXEL) {
            flameRight.add(new Flame(model, coord, new Action(false, false, false, true)));
            hasChange = true;
        }

        coord = (Point2D.Double)flameLeft.get(flameLeft.size()-1).getImagePoint();
        coord.x -= ADD_PIXEL;
        if(!sprites.stream().anyMatch(n -> flameLeft.get(flameLeft.size()-1).isIntersect(n)) && coord.getX() > areaPoint.getX() - radius - 3*ADD_PIXEL) {
            flameLeft.add(new Flame(model, coord, new Action(false, false, true, false)));
            hasChange = true;
        }

        coord = (Point2D.Double)flameDown.get(flameDown.size()-1).getImagePoint();
        coord.y += ADD_PIXEL;
        if(!sprites.stream().anyMatch(n -> flameDown.get(flameDown.size()-1).isIntersect(n)) && coord.getY() < radius + areaPoint.getY() + 3*ADD_PIXEL) {
            flameDown.add(new Flame(model, coord, new Action(false, true, false, false)));
            hasChange = true;
        }

        coord = (Point2D.Double)flameUp.get(flameUp.size()-1).getImagePoint();
        coord.y -= ADD_PIXEL;
        if(!sprites.stream().anyMatch(n -> flameUp.get(flameUp.size()-1).isIntersect(n)) && coord.getY() > areaPoint.getY() - radius - 3*ADD_PIXEL) {
            flameUp.add(new Flame(model, coord, new Action(true, false, false, false)));
            hasChange = true;
        }
        
        if(flameUp.size() >= startFlameIndex || flameDown.size() >= startFlameIndex ||
           flameRight.size() >= startFlameIndex || flameLeft.size() >= startFlameIndex) {
            if(!hasChange) {
                startFlameIndex += 1;
            }
            executorService.schedule(() -> destructor(), 10, TimeUnit.MILLISECONDS);
        }
        else {
            this.model.deleteSpriteFromBoard(this);
        }
    }
    
}
