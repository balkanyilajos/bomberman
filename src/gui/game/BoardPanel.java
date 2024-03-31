package gui.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.GameModel;
import model.sprite.Sprite;

public class BoardPanel extends JPanel {
    private GameModel gameModel;
    private Dimension pixelSize;
    private Image grass;

    public BoardPanel(GameModel gameModel) {
        this.gameModel = gameModel;
        this.pixelSize = gameModel.getBoardPixelSize();
        try {
            this.grass = ImageIO.read(new File("src/data/picture/grass.jpg"));
        }
        catch(IOException e) {
            System.out.println(e);
        }
        
        setPreferredSize(pixelSize);
        setMinimumSize(pixelSize);
    }

    private void createBackground(Graphics graphics) {
        Dimension grassSize = gameModel.getCubeSize();
        
        for(int x = 0; x < pixelSize.width; x += grassSize.width) {
            for(int y = 0; y < pixelSize.height; y += grassSize.height) {
                graphics.drawImage(grass, x, y, grassSize.width, grassSize.height, null);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g = (Graphics2D) grphcs;

        createBackground(g);
        for(Sprite sprite : gameModel.getBoardSprites()) {
            sprite.draw(g);
        }
        
    }
    
}
