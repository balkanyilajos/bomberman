package model.sprite.moveable;

import gui.game.BoardPanel;
import model.GameModel;
import model.sprite.Sprite;
import model.util.Action;
import model.util.PlayerAction;

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
