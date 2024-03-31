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
        mainPanel = createMainPanel();
        menuDialog = new MenuDialog(this);
        gameField = new GamePanel(new GameModel(this, mapPath, playerNumber, wonRoundNumber));
        layout = new OverlayLayout(mainPanel);
        mainPanel.setLayout(layout);
        add(mainPanel);

        mainPanel.add(menuDialog);
        mainPanel.add(gameField);

        addKeyListener(createMenuDialogAdapter());
        pack();

        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Dimension panelSize = gameField.getMinimumSize();
        Insets frameSize = getInsets();
        setMinimumSize(new Dimension(panelSize.width + frameSize.left + frameSize.right, panelSize.height + frameSize.bottom + frameSize.top));
    }

    public void repaint() {
        gameField.repaint();
    }

    private KeyAdapter createMenuDialogAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if(keyCode == KeyEvent.VK_ESCAPE) {
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
