package gui.game;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.GameModel;

public class GameWindow extends JFrame {
    JPanel panel;

    public GameWindow(String mapPath, int playerNumber, int wonRoundNumber) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setResizable(false);
        panel = new GamePanel(new GameModel(this, mapPath, playerNumber, wonRoundNumber));
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.addKeyListener(new MainKeyListener());
    }

    public void repaint() {
        panel.repaint();
    }
}
