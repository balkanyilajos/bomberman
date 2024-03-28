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

    public void startGame() {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setVisible(true);
        dispose();
    }

}
