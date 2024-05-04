package model.sprite.fixedelement;

import java.awt.Dimension;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import java.awt.Image;

import model.GameModel;
import model.sprite.Sprite;
import model.sprite.powerup.*;

public class Box extends Sprite {
    private ArrayList<Image> elimination;
    private double elapsedTime;
    private boolean isDestroyed;
    private int eliminationIndex;

    public Box(GameModel model, Point2D point) {
        this(model, point, model.getCubeSize(), new Point2D.Double(point.getX() - model.getCubeSize().width * 2 * 0.3, point.getY() - model.getCubeSize().height * 1.4 * 0.18), new Dimension((int) (model.getCubeSize().width * 2), (int) (model.getCubeSize().height * 1.4)));
    }

    public Box(GameModel model, Point2D areaPoint, Dimension areaSize, Point2D imagePoint, Dimension imageSize) {
        super(model, new Area(new Rectangle2D.Double(areaPoint.getX(), areaPoint.getY(), areaSize.width, areaSize.height)),
            imagePoint, areaPoint, imageSize, "src/data/picture/box/0.png");
        
        elimination = new ArrayList<>();
        elapsedTime = 0;
        isDestroyed = false;
        eliminationIndex = 0;
        System.out.println("alap: " + areaPoint +" "+ areaSize);
        for (int i = 1; i <= 8; i++) {
            try {
                elimination.add(ImageIO.read(new File("src/data/picture/box/" + i + ".png")));
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
                Random ran = new Random(); 
                int pu = ran.nextInt(6);
                if(pu == 0)
                {
                    BombBooster bb = new BombBooster(model, areaPoint);
                    model.addSpriteToBoard(bb);
                }
                else if(pu == 1)
                {
                    Invulnerability i = new Invulnerability(model, areaPoint);
                    model.addSpriteToBoard(i);
                }
                else if(pu == 2)
                {
                    BlastBooster bb = new BlastBooster(model, areaPoint);
                    model.addSpriteToBoard(bb);
                }
                else if(pu == 3)
                {
                    RollerSkates rs = new RollerSkates(model, areaPoint);
                    model.addSpriteToBoard(rs);
                }
                else if(pu == 4)
                {
                    Detonator d = new Detonator(model, areaPoint);
                    model.addSpriteToBoard(d);
                }
                System.out.println(pu);
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
