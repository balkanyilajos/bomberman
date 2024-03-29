package gui.game;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.GameModel;

public class GameWindow extends JFrame {
    JPanel panel;

    public GameWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        panel = new GamePanel(new GameModel(this));
        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    public void repaint() {
        panel.repaint();
    }
}
