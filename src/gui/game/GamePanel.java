package gui.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.GameModel;

public class GamePanel extends JPanel {
    private Image background;
    private Dimension screenSize;
    private Dimension backgroundSize;
    private Point backgroundPoint;
    
    public GamePanel(GameModel gameModel) {
        setLayout(new GridBagLayout());
        try {
            background = ImageIO.read(new File("src/data/picture/background.jpg"));
        }
        catch (IOException e) {
            System.out.println(e);
        }

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        calculateBackground();
        Dimension maximumBoardSize = new Dimension(screenSize.width, (int)(screenSize.height*0.8));
        Dimension boardIndexSize = gameModel.getBoardIndexSize();
        gameModel.start(calculateCubeSize(maximumBoardSize, boardIndexSize));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new MenuPanel(gameModel), gbc);

        gbc.gridy = 1;
        add(new BoardPanel(gameModel), gbc);
    }

    private Dimension calculateCubeSize(Dimension screenSize, Dimension boardIndexSize) {
        int maxCubeWidth = (int) (screenSize.width / boardIndexSize.width);
        int maxCubeHeight = (int) (screenSize.height/ boardIndexSize.height);
        int size = Math.min(maxCubeWidth, maxCubeHeight);
        return new Dimension(size, size);
    }

    private void calculateBackground() {
        int width = background.getWidth(this);
        int height = background.getHeight(this);
        double ratio = width / height;

        if ((double) screenSize.width / screenSize.height < ratio) {
            int newWidth = (int)(screenSize.height * ratio);
            backgroundSize = new Dimension(newWidth, screenSize.height);
            backgroundPoint = new Point((width - newWidth) / 2, 0);
        } else {
            int newHeight = screenSize.width * height / width;
            backgroundSize = new Dimension(screenSize.width, newHeight);
            backgroundPoint = new Point(0, (height - newHeight) / 2);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, backgroundPoint.x, backgroundPoint.y, backgroundSize.width, backgroundSize.height, this);
    }

}
