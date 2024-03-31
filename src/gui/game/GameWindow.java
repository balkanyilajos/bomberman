package gui.game;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.GameModel;

public class GameWindow extends JFrame {
    JPanel panel;

    public GameWindow(String mapPath, int playerNumber, int wonRoundNumber) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new GamePanel(new GameModel(this, mapPath, playerNumber, wonRoundNumber));
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Dimension panelSize = panel.getMinimumSize();
        Insets frameSize = getInsets();
        setMinimumSize(new Dimension(panelSize.width + frameSize.left + frameSize.right, panelSize.height + frameSize.bottom + frameSize.top));
    }

    public void repaint() {
        panel.repaint();
    }
}
