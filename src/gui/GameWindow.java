package gui;

import javax.swing.JFrame;

import gui.game.GamePanel;
import model.GameModel;

public class GameWindow extends JFrame {

    public GameWindow(GameModel gameModel) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new GamePanel(gameModel));
        pack();
    }



}
