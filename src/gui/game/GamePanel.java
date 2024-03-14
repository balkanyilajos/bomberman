package gui.game;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import model.GameModel;

public class GamePanel extends JPanel {

    public GamePanel(GameModel gameModel) {
        setLayout(new BorderLayout());
        add(new BoardPanel(gameModel), BorderLayout.NORTH);
    }

}
