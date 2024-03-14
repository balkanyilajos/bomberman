package model.sprite.weapon;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.GameModel;
import model.sprite.Sprite;
import model.sprite.fixedelement.Wall;
import model.util.Action;

public class Flame extends Sprite {

    private Flame(GameModel model, Point2D point, Dimension size, Action direction) throws IOException {
        super(model, null, point, size, null);
        if(direction.isLeft() || direction.isRight()) {
            this.shape = new Area(new Ellipse2D.Double(point.getX(), point.getY(), size.getWidth(), size.getHeight()));
            if(direction.isLeft()) {
                this.actualImage = ImageIO.read(new File("src/data/picture/flame-left.png"));
            }
            else {
                this.actualImage = ImageIO.read(new File("src/data/picture/flame-right.png"));
            }         
        }
        else {
            this.shape = new Area(new Ellipse2D.Double(point.getX(), point.getY(), size.getWidth(), size.getHeight()));
            if(direction.isUp()) {
                this.actualImage = ImageIO.read(new File("src/data/picture/flame-up.png"));
            }
            else {
                this.actualImage = ImageIO.read(new File("src/data/picture/flame-down.png"));
            }
        }
    }
    
    @Override
    public boolean isIntersect(Sprite sprite) {
        if(!(sprite instanceof Wall)) {
            sprite.getShape().intersect(shape);
            return !shape.isEmpty();
        }
        return false;
    }

}
