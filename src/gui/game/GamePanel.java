package gui.game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import model.GameModel;

public class GamePanel extends JPanel {

    public GamePanel(GameModel gameModel) {
        setLayout(new BorderLayout());
        add(new BoardPanel(gameModel), BorderLayout.NORTH);
    }

}
