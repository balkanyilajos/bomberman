package model.sprite.fixedelement;

import java.awt.Dimension;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import model.GameModel;
import model.sprite.Sprite;

public class Box extends Sprite {

    public Box(GameModel model, Point2D imagePoint) {
        this(model, imagePoint, model.getCubeSize());
    }

    public Box(GameModel model, Point2D imagePoint, Dimension imageSize) {
        super(model, new Area(new Rectangle2D.Double(imagePoint.getX(), imagePoint.getY(), imageSize.getWidth(), imageSize.getHeight())),
            imagePoint, (Point2D)imagePoint.clone(), imageSize, "src/data/picture/box.png");
    }
    
}
