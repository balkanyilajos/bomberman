package model;

import javax.swing.JFrame;
import gui.GameWindow;

public class GameModel {
    final JFrame window;

    public GameModel() {
        window = new GameWindow();
        window.setVisible(true);
    }
}
