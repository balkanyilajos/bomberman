package model.sprite.weapon;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.*;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

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

    private Bomb(GameModel model, Point2D point, double radius, Dimension size) throws IOException {
        super(model, new Area(new Ellipse2D.Double(point.getX(), point.getY(), size.getWidth(), size.getHeight())),
              point, size, ImageIO.read(new File("src/data/picture/bomb.png")));
        
        this.radius = radius;
        this.size = size;
        this.isDestroyed = false;
        this.startFlameIndex = 0;
        Image flameUpImage = ImageIO.read(new File("src/data/picture/flame-up.png"));
        Image flameDownImage = ImageIO.read(new File("src/data/picture/flame-down.png"));
        Image flameLeftImage = ImageIO.read(new File("src/data/picture/flame-left.png"));
        Image flameRightImage = ImageIO.read(new File("src/data/picture/flame-right.png"));
        this.flameUp = new ArrayList<>();
        this.flameDown = new ArrayList<>();
        this.flameRight = new ArrayList<>();
        this.flameLeft = new ArrayList<>();
        flameUp.add(new Flame(model, point, size, null));
        flameDown.add(point);
        flameRight.add(point);
        flameLeft.add(point);
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void draw(Graphics graphics) {
        if(isDestroyed) {
            for(int i = startFlameIndex; i < flameUp.size(); i++) {
                graphics.drawImage(flameUpImage, (int)flameUp.get(i).getX(), (int)flameUp.get(i).getY(), (int)size.getWidth(), (int)size.getHeight(), null);
            }
            for(int i = startFlameIndex; i < flameDown.size(); i++) {
                graphics.drawImage(flameDownImage, (int)flameDown.get(i).getX(), (int)flameDown.get(i).getY(), (int)size.getWidth(), (int)size.getHeight(), null);
            }
            for(int i = startFlameIndex; i < flameLeft.size(); i++) {
                graphics.drawImage(flameLeftImage, (int)flameLeft.get(i).getX(), (int)flameLeft.get(i).getY(), (int)size.getWidth(), (int)size.getHeight(), null);
            }
            for(int i = startFlameIndex; i < flameRight.size(); i++) {
                graphics.drawImage(flameRightImage, (int)flameRight.get(i).getX(), (int)flameRight.get(i).getY(), (int)size.getWidth(), (int)size.getHeight(), null);
            }
        }
        else {
            graphics.drawImage(actualImage, (int)point.getX(), (int)point.getY(), (int)size.getWidth(), (int)size.getHeight(), null);
        }
    }

    @Override
    public void destructor() {
        boolean hasChange = false;
        if(flameRight.get(flameRight.size()-1).getX() + 10 < radius + point.getX()) {
            flameRight.add(new Point2D.Double(flameRight.get(flameRight.size()-1).getX() + 10, point.getY()));
            hasChange = true;
        }
        if(flameLeft.get(flameLeft.size()-1).getX() - 1 > point.getX() - radius) {
            flameLeft.add(new Point2D.Double(flameLeft.get(flameLeft.size()-1).getX() - 10, point.getY()));
            hasChange = true;
        }
        if(flameDown.get(flameDown.size()-1).getY() + 1 < radius + point.getY()) {
            flameDown.add(new Point2D.Double(point.getX(), flameDown.get(flameDown.size()-1).getY() + 10));
            hasChange = true;
        }
        if(flameUp.get(flameUp.size()-1).getY() - 1 > point.getY() - radius) {
            flameUp.add(new Point2D.Double(point.getX(), flameUp.get(flameUp.size()-1).getY() - 10));
            hasChange = true;
        }
        
        if(flameUp.size() >= startFlameIndex && flameDown.size() >= startFlameIndex &&
           flameRight.size() >= startFlameIndex && flameLeft.size() >= startFlameIndex) {
            if(!hasChange) {
                startFlameIndex += 1;
            }
            executorService.schedule(() -> destructor(), 40, TimeUnit.MILLISECONDS);
        }
        else {
            this.model.deleteSpriteFromBoard(this);
        }
    }
    
}
