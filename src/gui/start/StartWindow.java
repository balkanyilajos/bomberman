package gui.start;

import javax.swing.JFrame;
import gui.game.GameWindow;

public class StartWindow extends JFrame {
    public StartWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(new StartPanel(this));
        pack();
        setLocationRelativeTo(null);
    }

    public void startGame(String mapPath, int playerNumber, int wonRoundNumber) {
        GameWindow gameWindow = new GameWindow(mapPath, playerNumber, wonRoundNumber);
        gameWindow.setVisible(true);
        dispose();
    }

}
