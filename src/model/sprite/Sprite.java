package model.sprite;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

import model.GameModel;

public abstract class Sprite {
    protected GameModel model;
    protected Area shape;
    protected Point2D point;
    protected Dimension size;
    protected Image actualImage;

    public Sprite(GameModel model, Area shape, Point2D point, Dimension size, Image actualImage) {
        this.model = model;
        this.shape = shape;
        this.point = point;
        this.size = size;
        this.actualImage = actualImage;
    }

    public Point2D getPoint() {
        return (Point2D)this.point.clone();
    }

    public Area getShape() {
        return (Area)this.shape.clone();
    }

    public boolean isIntersect(Sprite sprite) {
        return isIntersect(sprite.shape);
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
