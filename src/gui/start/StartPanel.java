package gui.start;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import gui.game.GamePanel;
import gui.game.GameWindow;
import model.GameModel;

public class StartPanel extends JPanel {
    private Image background;
    private Dimension backgroundSize;
    private StartWindow window;

    private StartGamePanel startGamePanel;
    private MapSettingPanel mapSettingPanel;

    public StartPanel(StartWindow window) {
        setLayout(new GridBagLayout());
        this.window = window;
        this.backgroundSize = new Dimension(880, 560);
        this.startGamePanel = new StartGamePanel(this);
        this.mapSettingPanel = new MapSettingPanel(this);

        try {
            this.background = ImageIO.read(new File("src/data/picture/bomberman-background.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        add(mapSettingPanel);
        add(startGamePanel);
        goToStartGamePanel();

        setPreferredSize(backgroundSize);
    }

    public void goToMapSettingPanel() {
        startGamePanel.setVisible(false);
        mapSettingPanel.setVisible(true);
        repaint();
    }

    public void goToStartGamePanel() {
        startGamePanel.setVisible(true);
        mapSettingPanel.setVisible(false);
        repaint();
    }

    public StartWindow getWindow() {
        return window;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, (int) backgroundSize.getWidth(), (int) backgroundSize.getHeight(), null);
    }
}
