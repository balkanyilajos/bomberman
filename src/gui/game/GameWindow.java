package gui.game;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.GameModel;

public class GameWindow extends JFrame {
    JPanel panel;

    public GameWindow(String mapPath) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setResizable(false);

        panel = new GamePanel(new GameModel(this, mapPath));
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void repaint() {
        panel.repaint();
    }
}
