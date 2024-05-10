package hu.elte.fi.szofttech.bomberman.model.sprite.powerup;

import hu.elte.fi.szofttech.bomberman.gui.game.BoardPanel;
import hu.elte.fi.szofttech.bomberman.model.GameModel;
import hu.elte.fi.szofttech.bomberman.model.sprite.Sprite;
import hu.elte.fi.szofttech.bomberman.model.sprite.moveable.player.Player;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.awt.geom.Rectangle2D;

public abstract class PowerUp extends Sprite {

    protected double time;
    protected Player player;

    public PowerUp(GameModel model, Area area, Point2D imagePoint,
            Point2D areaPoint,
            Dimension imageSize,
            String imagePath, double time) {
        super(model, area, imagePoint, areaPoint, imageSize, imagePath);
        this.time = time;
        this.player = null;
    }

    public abstract boolean decreaseTime(double deltaTime);

    public abstract void effect(Player player);
}
