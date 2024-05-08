package hu.elte.fi.szofttech.bomberman.model.sprite.weapon;

import java.awt.Dimension;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import hu.elte.fi.szofttech.bomberman.model.GameModel;
import hu.elte.fi.szofttech.bomberman.model.sprite.Sprite;
import hu.elte.fi.szofttech.bomberman.model.util.Action;
import hu.elte.fi.szofttech.bomberman.GeneralPath;

public class Flame extends Sprite {

    public Flame(GameModel model, Point2D point, Action direction) {
        this(model, point,
                new Dimension((int) (model.getCubeSize().width * 0.8), (int) (model.getCubeSize().height * 0.8)),
                direction);
    }

    private Flame(GameModel model, Point2D imagePoint, Dimension size, Action direction) {
        super(model, null, imagePoint, (Point2D) imagePoint.clone(), size, null);
        try {
            if (direction.left || direction.right) {
                this.area = new Area(
                        new Ellipse2D.Double(areaPoint.getX(), areaPoint.getY(), size.getWidth(), size.getHeight()));
                if (direction.left) {
                    this.actualImage = ImageIO.read(new File(GeneralPath.getPath() + "/picture/bomb/flame-left.png"));
                } else {
                    this.actualImage = ImageIO.read(new File(GeneralPath.getPath() + "/picture/bomb/flame-right.png"));
                }
            } else {
                this.area = new Area(
                        new Ellipse2D.Double(areaPoint.getX(), areaPoint.getY(), size.getWidth(), size.getHeight()));
                if (direction.up) {
                    this.actualImage = ImageIO.read(new File(GeneralPath.getPath() + "/picture/bomb/flame-up.png"));
                } else {
                    this.actualImage = ImageIO.read(new File(GeneralPath.getPath() + "/picture/bomb/flame-down.png"));
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
