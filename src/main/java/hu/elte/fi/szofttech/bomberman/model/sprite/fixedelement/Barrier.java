package hu.elte.fi.szofttech.bomberman.model.sprite.fixedelement;

import hu.elte.fi.szofttech.bomberman.model.GameModel;
import hu.elte.fi.szofttech.bomberman.model.sprite.Sprite;
import hu.elte.fi.szofttech.bomberman.GeneralPath;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Barrier extends Sprite {
    private ArrayList<Image> elimination;
    private double elapsedTime;
    private boolean isDestroyed;
    private int eliminationIndex;

    public Barrier(GameModel model, Point2D point) {
        this(model, point,
                new Point2D.Double(point.getX() - model.getCubeSize().width * 2 * 0.24,
                        point.getY() - model.getCubeSize().height * 1.4 * 0.2),
                model.getCubeSize(),
                new Dimension((int) (model.getCubeSize().width * 2), (int) (model.getCubeSize().height * 1.4)));
    }

    public Barrier(GameModel model, Point2D areaPoint, Point2D imagePoint, Dimension areaSize, Dimension imageSize) {
        super(model, new Area(new Rectangle2D.Double(areaPoint.getX(), areaPoint.getY(), areaSize.getWidth(),
                areaSize.getHeight())), imagePoint, areaPoint, imageSize,
                GeneralPath.getPath() + "/picture/barrier/0.png");

        isDestroyed = false;
        elapsedTime = 0;
        eliminationIndex = 0;
        elimination = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            try {
                elimination.add(ImageIO.read(new File(GeneralPath.getPath() + "/picture/barrier/" + i + ".png")));
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }

    @Override
    public void destructor() {
        isDestroyed = true;

        if (elapsedTime >= 0.1) {
            if (eliminationIndex >= elimination.size()) {
                super.destructor();
                return;
            }

            actualImage = elimination.get(eliminationIndex++);
            elapsedTime = 0;
        }
    }

    @Override
    public void update(double deltaTime) {
        if (isDestroyed) {
            elapsedTime += deltaTime;
            destructor();
        }
    }

}
