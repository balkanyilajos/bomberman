package hu.elte.fi.szofttech.bomberman.model.sprite.fixedelement;

import java.awt.Dimension;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import hu.elte.fi.szofttech.bomberman.model.GameModel;
import hu.elte.fi.szofttech.bomberman.model.sprite.Sprite;
import hu.elte.fi.szofttech.bomberman.GeneralPath;

public class Wall extends Sprite {

    public Wall(GameModel model, Point2D point) {
        this(model, point, model.getCubeSize());
    }

    private Wall(GameModel model, Point2D imagePoint, Dimension size) {
        super(model,
                new Area(new Rectangle2D.Double(imagePoint.getX(), imagePoint.getY(), size.getWidth(),
                        size.getHeight())),
                imagePoint, (Point2D) imagePoint.clone(), size, GeneralPath.getPath() + "/picture/wall.png");
    }

}
