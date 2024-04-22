package model.sprite.moveable.enemy;

import java.util.ArrayList;
import java.util.HashSet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import model.GameModel;
import model.sprite.Sprite;
import model.sprite.fixedelement.Barrier;
import model.sprite.moveable.MoveableSprite;
import model.sprite.weapon.Bomb;
import model.sprite.moveable.player.Player;
import model.util.Action;
import model.util.PlayerAction;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.Random;

public class Balloon extends MoveableSprite {

    private int otherDirection;

    public Balloon(GameModel model, Point2D imagePoint, String imagePath) {
        this(model, new Action(true, false, false, false), imagePoint, imagePath);
    }

    public Balloon(GameModel model, Action action, Point2D imagePoint, String imagePath) {
        this(model, action, imagePoint, model.getCubeSize(), imagePath, 75);
    }

    private Balloon(GameModel model, Action action, Point2D imagePoint, Dimension imageSize, String imagePath,
            int speed) {
        super(model, null, action, speed, imagePoint, null, imageSize, imagePath);
        this.areaPoint = new Point2D.Double(imagePoint.getX(),
                imagePoint.getY());
        this.area = newArea();
        setOtherDirection();
    }

    private void setOtherDirection()
    {
        Random rnd = new Random();
        int N = (int) (model.getBoardIndexSize().getWidth()*model.getBoardIndexSize().getHeight()*10);
        int n = rnd.nextInt(N);
        //System.out.println(N+" ** "+n);
        otherDirection = n;
    }

    private Area newArea()
    {
        return new Area(new Rectangle2D.Double(
            imagePoint.getX()+10, imagePoint.getY()+2,
            model.getCubeSize().getWidth()*0.75, model.getCubeSize().getHeight()));
    }

    @Override
    public void move(double deltaTime) {
        model.deleteSpriteFromBoard(this);
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
            Area newArea = newArea();
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
            Area newArea = newArea();
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
            Area newArea = newArea();
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
            Area newArea = newArea();
            if (isMoveable(newAreaPoint)) {
                areaPoint = newAreaPoint;
                imagePoint = newImagePoint;
                area = newArea;
            }
        }
        model.addSpriteToBoard(this);
    }

    @Override
    public boolean isMoveable(Point2D point) {
        HashSet<Sprite> sprites = model.getBoardSprites(point, model.getCubeSize().getWidth());
        sprites.remove(this);
        for (Sprite sprite : sprites) {
            if (isIntersect(sprite, point)) {
                action.changeDirection();
                if(sprite instanceof Player)
                {
                    sprite.destructor();
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void update(double deltaTime) {
        move(deltaTime);
        otherDirection--;
        if(otherDirection == 0)
        {
            setOtherDirection();
            action.changeDirection();
        }
    }

    @Override
    public void draw(Graphics graphics) {
        super.draw(graphics);
        Graphics2D gr = (Graphics2D) graphics;
        gr.fill(area);
    }

}