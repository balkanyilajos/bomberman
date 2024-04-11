package gui.game;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.PublicKey;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import gui.dialog.MenuDialog;
import model.GameModel;
import model.util.PlayerAction;

public class GameWindow extends JFrame {
    OverlayLayout layout;
    JPanel gameField;
    JPanel menuDialog;
    JPanel mainPanel;
    ArrayList<PlayerAction> actions;

    public GameWindow(String mapPath, int playerNumber, int wonRoundNumber) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = createMainPanel();
        menuDialog = new MenuDialog(this);
        actions = new ArrayList<>();

        for (int i = 0; i < playerNumber; i++) {
            actions.add(new PlayerAction());
            addKeyListener(playerAdapter(actions.get(i)));
        }

        gameField = new GamePanel(new GameModel(this, mapPath, wonRoundNumber, actions));
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
        setMinimumSize(new Dimension(panelSize.width + frameSize.left + frameSize.right,
                panelSize.height + frameSize.bottom + frameSize.top));

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

    private KeyAdapter playerAdapter(PlayerAction action) {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_W) {
                    action.up = true;
                }

                if (keyCode == KeyEvent.VK_A) {
                    action.left = true;
                }

                if (keyCode == KeyEvent.VK_S) {
                    action.down = true;
                }

                if (keyCode == KeyEvent.VK_D) {
                    action.right = true;
                }

                if (keyCode == KeyEvent.VK_Q) {
                    action.placeBomb = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_W) {
                    action.up = false;
                }

                if (keyCode == KeyEvent.VK_A) {
                    action.left = false;
                }

                if (keyCode == KeyEvent.VK_S) {
                    action.down = false;
                }

                if (keyCode == KeyEvent.VK_D) {
                    action.right = false;
                }

                if (keyCode == KeyEvent.VK_Q) {
                    action.placeBomb = false;
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
