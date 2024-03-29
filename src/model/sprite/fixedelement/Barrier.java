package model.sprite.fixedelement;

import model.GameModel;
import model.sprite.Sprite;

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

    public Barrier(GameModel model, Point2D imagePoint) {
        this(model,
                new Point2D.Double(imagePoint.getX() - model.getCubeSize().width * 2 * 0.24,
                        imagePoint.getY() - model.getCubeSize().height * 1.4 * 0.2),
                new Dimension((int) (model.getCubeSize().width * 2), (int) (model.getCubeSize().height * 1.4)));
    }

    public Barrier(GameModel model, Point2D imagePoint, Dimension imageSize) {
        super(model,
                new Area(new Rectangle2D.Double(imagePoint.getX(), imagePoint.getY(), imageSize.getWidth(),
                        imageSize.getHeight())),
                imagePoint, (Point2D) imagePoint.clone(), imageSize, "src/data/picture/barrier/0.png");

        isDestroyed = false;
        elapsedTime = 0;
        eliminationIndex = 0;
        elimination = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            try {
                elimination.add(ImageIO.read(new File("src/data/picture/barrier/" + i + ".png")));
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }

    @Override
    public void destructor() {
        isDestroyed = true;

        if(elapsedTime >= 0.1) {
            if(eliminationIndex >= elimination.size()) {
                super.destructor();
                return;
            }
            
            actualImage = elimination.get(eliminationIndex++);
            elapsedTime = 0;
        }
    }

    public void update(double deltaTime) {
        if(isDestroyed) {
            elapsedTime += deltaTime;
            destructor();
        }
    }

}
