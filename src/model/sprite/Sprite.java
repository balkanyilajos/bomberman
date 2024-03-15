package model.sprite;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.GameModel;

public abstract class Sprite {
    protected GameModel model;
    protected Area area;
    protected Point2D imagePoint;
    protected Point2D areaPoint;
    protected Dimension imageSize;
    protected Image actualImage;

    public Sprite(GameModel model, Area area, Point2D imagePoint, Point2D areaPoint, Dimension imageSize,
            String imagePath) {
        this.model = model;
        this.area = area;
        this.imagePoint = imagePoint;
        this.areaPoint = areaPoint;
        this.imageSize = imageSize;
        if (imagePath != null) {
            try {
                this.actualImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public Point2D getImagePoint() {
        return (Point2D) this.imagePoint.clone();
    }

    public Point2D getAreaPoint() {
        return (Point2D) this.areaPoint.clone();
    }

    public Area getArea() {
        return (Area) this.area.clone();
    }

    public boolean isIntersect(Sprite sprite) {
        return isIntersect(sprite.area);
    }

    public boolean isIntersect(Area area) {
        Area area2 = (Area) this.area.clone();
        area2.intersect(area);
        return !area2.isEmpty();
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(actualImage, (int) imagePoint.getX(), (int) imagePoint.getY(), (int) imageSize.getWidth(),
                (int) imageSize.getHeight(), null);
    }

    public void destructor() {
    }

    public void update(long deltaTime) {
    }

}
