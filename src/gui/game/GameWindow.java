package gui.game;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import gui.dialog.MenuDialog;
import model.GameModel;

public class GameWindow extends JFrame {
    OverlayLayout layout;
    JPanel gameField;
    JPanel menuDialog;
    JPanel mainPanel;

    public GameWindow(String mapPath, int playerNumber, int wonRoundNumber) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setResizable(false);
        panel = new GamePanel(new GameModel(this, mapPath, playerNumber, wonRoundNumber));
        add(panel);
        pack();

        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void repaint() {
        gameField.repaint();
    }

    private KeyAdapter createMenuDialogAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ESCAPE) {
                    menuDialog.setVisible(true);
                }
            }
        };
    }

    private JPanel createMainPanel() {
        return new JPanel() {
            @Override
            public boolean isOptimizedDrawingEnabled() {
                return false;
            }
        };
    }

}
