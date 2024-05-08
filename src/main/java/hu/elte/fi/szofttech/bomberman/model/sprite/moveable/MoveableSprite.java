package hu.elte.fi.szofttech.bomberman.model.sprite.moveable;

import hu.elte.fi.szofttech.bomberman.gui.game.BoardPanel;
import hu.elte.fi.szofttech.bomberman.model.GameModel;
import hu.elte.fi.szofttech.bomberman.model.sprite.Sprite;
import hu.elte.fi.szofttech.bomberman.model.util.Action;
import hu.elte.fi.szofttech.bomberman.model.util.PlayerAction;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

public abstract class MoveableSprite extends Sprite {

    protected PlayerAction action;
    protected int speed;

    public MoveableSprite(GameModel model, Area area, PlayerAction action, int speed, Point2D imagePoint,
            Point2D areaPoint,
            Dimension imageSize,
            String imagePath) {
        super(model, area, imagePoint, areaPoint, imageSize, imagePath);
        this.action = action;
        this.speed = speed;
    }

    protected abstract void move(double deltaTime);

    protected abstract boolean isMoveable(Point2D point);
}
