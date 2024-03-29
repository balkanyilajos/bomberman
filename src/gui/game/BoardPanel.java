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
    private Dimension dimension;
    private Image grass;

    public BoardPanel(GameModel gameModel) {
        this.gameModel = gameModel;
        try {
            this.grass = ImageIO.read(new File("src/data/picture/grass.jpg"));
        }
        catch(IOException e) {
            System.out.println(e);
        }
        
        dimension = new Dimension(600, 600);
        gameModel.init((Dimension)dimension.clone());
        setPreferredSize(dimension);
    }

    private void createBackground(Graphics graphics) {
        Dimension grassDimension = new Dimension((int)(dimension.getWidth()/10), (int)(dimension.getHeight()/10));
        
        double width = 0;
        while(width < dimension.getWidth()) {
            double height = 0;
            while(height < dimension.getHeight()) {
                graphics.drawImage(grass, (int)width, (int)height, (int)grassDimension.getWidth(), (int)grassDimension.getHeight(), null);
                height += grassDimension.getHeight();
            }
            width += grassDimension.getWidth();
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
