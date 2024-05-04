package gui.game;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import gui.dialog.MenuDialog;
import model.GameModel;
import model.KeyReaderWriter;
import model.util.PlayerAction;

public class GameWindow extends JFrame {
    OverlayLayout layout;
    JPanel gameField;
    JPanel menuDialog;
    JPanel mainPanel;
    ArrayList<PlayerAction> actions;
    private KeyReaderWriter keyReaderWriter;

    public GameWindow(String mapPath, int playerNumber, int wonRoundNumber) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = createMainPanel();
        menuDialog = new MenuDialog(this);
        actions = new ArrayList<>();
        int[][] playerKeys = new int[3][6];

        // Keyactions
        try {
            this.keyReaderWriter = new KeyReaderWriter("src/data/keyboard/keyboard.txt");
            playerKeys[0] = this.keyReaderWriter.getMoves(1);
            playerKeys[1] = this.keyReaderWriter.getMoves(2);
            playerKeys[2] = this.keyReaderWriter.getMoves(3);
            // System.out.println(playerKeys1.length);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

        for (int i = 0; i < playerNumber; i++) {
            actions.add(new PlayerAction());
        }
        addKeyListener(playerAdapter(actions, playerKeys));

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

    private int[] keyArrayForPlayers() {
        try {
            this.keyReaderWriter = new KeyReaderWriter("src/data/keyboard/keyboard.txt");

            int[] temp = this.keyReaderWriter.getMoves(1);

            return temp;
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
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

    private KeyAdapter playerAdapter(ArrayList<PlayerAction> actions, int[][] keys) {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                // Player1
                if (keyCode == keys[0][0]) {
                    actions.get(0).up = true;
                }

                if (keyCode == keys[0][1]) {
                    actions.get(0).left = true;
                }

                if (keyCode == keys[0][2]) {
                    actions.get(0).down = true;
                }

                if (keyCode == keys[0][3]) {
                    actions.get(0).right = true;
                }

                if (keyCode == keys[0][4]) {
                    actions.get(0).placeBomb = true;
                }

                // Player2

                if (keyCode == keys[1][0]) {
                    actions.get(1).up = true;
                }

                if (keyCode == keys[1][1]) {
                    actions.get(1).left = true;
                }

                if (keyCode == keys[1][2]) {
                    actions.get(1).down = true;
                }

                if (keyCode == keys[1][3]) {
                    actions.get(1).right = true;
                }

                if (keyCode == keys[1][4]) {
                    actions.get(1).placeBomb = true;
                }

                // Player3

                if (actions.size() > 2) {
                    if (keyCode == keys[2][0]) {
                        actions.get(2).up = true;
                    }

                    if (keyCode == keys[2][1]) {
                        actions.get(2).left = true;
                    }

                    if (keyCode == keys[2][2]) {
                        actions.get(2).down = true;
                    }

                    if (keyCode == keys[2][3]) {
                        actions.get(2).right = true;
                    }

                    if (keyCode == keys[2][4]) {
                        actions.get(2).placeBomb = true;
                    }
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == keys[0][0]) {
                    actions.get(0).up = false;
                }

                if (keyCode == keys[0][1]) {
                    actions.get(0).left = false;
                }

                if (keyCode == keys[0][2]) {
                    actions.get(0).down = false;
                }

                if (keyCode == keys[0][3]) {
                    actions.get(0).right = false;
                }

                if (keyCode == keys[0][4]) {
                    actions.get(0).placeBomb = false;
                }

                // Player2

                if (keyCode == keys[1][0]) {
                    actions.get(1).up = false;
                }

                if (keyCode == keys[1][1]) {
                    actions.get(1).left = false;
                }

                if (keyCode == keys[1][2]) {
                    actions.get(1).down = false;
                }

                if (keyCode == keys[1][3]) {
                    actions.get(1).right = false;
                }

                if (keyCode == keys[1][4]) {
                    actions.get(1).placeBomb = false;
                }

                // Player3

                if (actions.size() > 2) {
                    if (keyCode == keys[2][0]) {
                        actions.get(2).up = false;
                    }

                    if (keyCode == keys[2][1]) {
                        actions.get(2).left = false;
                    }

                    if (keyCode == keys[2][2]) {
                        actions.get(2).down = false;
                    }

                    if (keyCode == keys[2][3]) {
                        actions.get(2).right = false;
                    }

                    if (keyCode == keys[2][4]) {
                        actions.get(2).placeBomb = false;
                    }
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
