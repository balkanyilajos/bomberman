package model.sprite.fixedelement;

import java.awt.Dimension;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.GameModel;
import model.sprite.Sprite;

public class Wall extends Sprite {
    
    public Wall(GameModel model, Point2D point) throws IOException {
        this(model, point, model.getCubeSize());
    }

    private Wall(GameModel model, Point2D point, Dimension size) throws IOException {
        super(model, new Area(new Rectangle2D.Double(point.getX(), point.getY(), size.getWidth(), size.getHeight())),
              point, size, ImageIO.read(new File("src/data/picture/wall.png")));
    }

}
