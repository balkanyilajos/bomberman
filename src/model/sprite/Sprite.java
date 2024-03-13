package model.sprite;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Area;

import model.GameModel;

public abstract class Sprite {
    GameModel model;
    Area shape;
    Point point;
    Dimension size;
    Image actualImage;

    public Sprite(GameModel model, Area shape, Point point, Dimension size, Image actualImage) {
        this.model = model;
        this.shape = shape;
        this.point = point;
        this.size = size;
        this.actualImage = actualImage;
    }

    public boolean isIntersect(Sprite sprite) {
        Area shape = (Area)this.shape.clone();
        shape.intersect(sprite.shape);
        return !shape.isEmpty();
    }

    public boolean isIntersect(Area area) {
        Area shape = (Area)this.shape.clone();
        shape.intersect(area);
        return !shape.isEmpty();
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(actualImage, (int)point.getX(), (int)point.getY(), (int)size.getWidth(), (int)size.getHeight(), null);
    }

    public void destructor() {

    }

    public void update(long deltaTime) {

    }

}
