package model.sprite.powerup;

import gui.game.BoardPanel;
import model.GameModel;
import model.sprite.Sprite;
import model.sprite.moveable.player.Player;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class BombBooster extends PowerUp {

    public BombBooster(GameModel model, Point2D point) {
        this(model, point, model.getCubeSize(), new Point2D.Double(point.getX() - model.getCubeSize().width * 2 * 0.3, point.getY() - model.getCubeSize().height * 1.4 * 0.18), new Dimension((int) (model.getCubeSize().width * 2), (int) (model.getCubeSize().height * 1.4)));
    }

    public BombBooster(GameModel model, Point2D areaPoint, Dimension areaSize, Point2D imagePoint, Dimension imageSize) {
        super(model, new Area(new Rectangle2D.Double(areaPoint.getX(), areaPoint.getY(), areaSize.width, areaSize.height)),
            imagePoint, areaPoint, imageSize, "src/data/picture/powerup/shield.png");
    }

    @Override
    public void effect(Player player)
    {}
}
