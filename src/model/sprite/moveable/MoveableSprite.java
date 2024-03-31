package model.sprite.moveable;

import gui.game.BoardPanel;
import model.GameModel;
import model.sprite.Sprite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

public abstract class MoveableSprite extends Sprite {

    public MoveableSprite(GameModel model, Area area, Point2D imagePoint, Point2D areaPoint, Dimension imageSize,
            String imagePath) {
        super(model, area, imagePoint, areaPoint, imageSize, imagePath);
    }

    protected abstract void moveUp(double deltaTime);

    protected abstract void moveDown(double deltaTime);

    protected abstract void moveLeft(double deltaTime);

    protected abstract void moveRight(double deltaTime);
}
