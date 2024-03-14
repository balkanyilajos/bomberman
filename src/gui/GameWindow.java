package gui;

import javax.swing.JFrame;

import gui.game.GamePanel;
import model.GameModel;

public class GameWindow extends JFrame {

    GamePanel gamePanel;

    public GameWindow(GameModel gameModel) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new GamePanel(gameModel);
        add(gamePanel);
        pack();
    }

    public void repaint() {
        gamePanel.repaint();
    }

}
