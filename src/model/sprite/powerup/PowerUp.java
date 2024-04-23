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
import java.io.File;
import java.io.IOException;
import java.awt.geom.Rectangle2D;

public abstract class PowerUp extends Sprite {

    public PowerUp(GameModel model, Area area, Point2D imagePoint,
            Point2D areaPoint,
            Dimension imageSize,
            String imagePath) 
    {
        super(model, area, imagePoint, areaPoint, imageSize, imagePath);
    }

    public abstract void effect(Player player);
}
