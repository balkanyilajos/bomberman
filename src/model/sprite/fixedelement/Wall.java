package model.sprite.fixedelement;

import java.awt.Dimension;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import model.GameModel;
import model.sprite.Sprite;

public class Wall extends Sprite {
    
    public Wall(GameModel model, Point2D point) {
        this(model, point, model.getCubeSize());
    }

    private Wall(GameModel model, Point2D imagePoint, Dimension size) {
        super(model, new Area(new Rectangle2D.Double(imagePoint.getX(), imagePoint.getY(), size.getWidth(), size.getHeight())),
            imagePoint, (Point2D)imagePoint.clone(), size, "src/data/picture/wall.png");
    }

}
